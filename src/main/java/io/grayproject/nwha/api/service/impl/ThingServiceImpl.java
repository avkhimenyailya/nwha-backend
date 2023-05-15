package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.domain.Profile;
import io.grayproject.nwha.api.domain.ProfileTask;
import io.grayproject.nwha.api.domain.Thing;
import io.grayproject.nwha.api.dto.RecentlyAddedThingDTO;
import io.grayproject.nwha.api.dto.ThingDTO;
import io.grayproject.nwha.api.exception.EntityNotFoundException;
import io.grayproject.nwha.api.mapper.ThingMapper;
import io.grayproject.nwha.api.repository.ProfileRepository;
import io.grayproject.nwha.api.repository.ProfileTaskRepository;
import io.grayproject.nwha.api.repository.ThingRepository;
import io.grayproject.nwha.api.service.ProfileService;
import io.grayproject.nwha.api.service.ThingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ThingServiceImpl implements ThingService {
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;

    private final ThingMapper thingMapper;
    private final ThingRepository thingRepository;
    private final ProfileTaskRepository profileTaskRepository;

    @Override
    public List<ThingDTO> getRandomThings(Integer limit, Integer taskOrdinalNumber) {
        List<Thing> all = thingRepository.findAll();
        Collections.shuffle(all);

        if (taskOrdinalNumber != null) {
            all.removeIf(thing -> !Objects.equals(thing.getProfileTask().getTask().getOrdinalNumber(), taskOrdinalNumber));
        }

        return all.stream()
                .filter(thing -> !thing.isRemoved())
                .map(thingMapper)
                .limit(limit)
                .toList();
    }

    @Override
    public List<RecentlyAddedThingDTO> getRecentlyAddedThings() {
        List<Thing> all = thingRepository.findAll();
        List<RecentlyAddedThingDTO> list = new ArrayList<>(all
                .stream()
                .filter(thing -> !thing.isRemoved())
                .sorted(Comparator.comparing(Thing::getCreatedAt))
                .map(thing -> RecentlyAddedThingDTO
                        .builder()
                        .thingId(thing.getId())
                        .thingFileUrl(thing.getFileUrl())
                        .taskOrdinalNumber(thing.getProfileTask().getTask().getOrdinalNumber())
                        .taskDescription(thing.getProfileTask().getTask().getDescription())
                        .username(thing.getProfileTask().getProfile().getUser().getUsername())
                        .addingTime(new PrettyTime(Locale.ENGLISH).format(thing.getCreatedAt()))
                        .build())
                .toList());
        Collections.reverse(list);
        return list;
    }

    @Override
    public ThingDTO getThingById(Long id) {
        return thingRepository
                .findById(id)
                .filter(thing -> !thing.isRemoved())
                .map(thingMapper)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    @Transactional
    public ThingDTO createThing(Principal principal, ThingDTO thingDTO) {
        Profile profile = profileService.getProfileEntityByPrincipal(principal);

        ProfileTask profileTask = profile
                .getProfileTasks()
                .stream()
                .filter(p -> p.getId().equals(thingDTO.profileTaskId()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("It is impossible to create thing for the specified profile task"));

        profileTask
                .getThings()
                .stream()
                .filter(thing -> !thing.isRemoved() && !thing.isArchived())
                .findFirst()
                .ifPresent(thing -> {
                    profileTask.getThings().forEach(System.out::println);
                    throw new RuntimeException("It is not possible to create a new Thing because the task already contains an actual Thing");
                });


        Thing thing = Thing
                .builder()
                .removed(false)
                .archived(false)
                .description(thingDTO.description())
                .profileTask(profileTask)
                .fileUrl(thingDTO.fileUrl())
                .build();

        if (profileTask.getThings() != null) {
            profileTask.getThings().add(thing);
        } else {
            profileTask.setThings(new ArrayList<>(List.of(thing)));
        }

        Thing saved = thingRepository.save(thing);
        profileTaskRepository.save(profileTask);
        return thingMapper.apply(saved);
    }

    @Override
    @Transactional
    public ThingDTO updateThing(Principal principal, ThingDTO thingDTO) {
        /* you can update the description and/or photo as well as delete and/or archive the item */
        Profile profile = profileService.getProfileEntityByPrincipal(principal);

        ProfileTask profileTask = profile
                .getProfileTasks()
                .stream()
                .filter(p -> p.getId().equals(thingDTO.profileTaskId()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("It is impossible to update thing for the specified profile task"));

        Thing originalThing = Optional
                .ofNullable(profileTask.getThings())
                .map(things -> things.stream()
                        .filter(thing -> !thing.isRemoved() && thing.getId().equals(thingDTO.id()))
                        .findFirst()
                        .get()
                )
                .orElseThrow(() -> new RuntimeException("Missing thing id"));

        originalThing.setArchived(thingDTO.archived());
        originalThing.setFileUrl(thingDTO.fileUrl());
        originalThing.setDescription(thingDTO.description());

        Thing save = thingRepository.save(originalThing);
        if (profileTask.getThings() != null) {
            profileTask.getThings().add(save);
        } else {
            profileTask.setThings(new ArrayList<>(List.of(save)));
        }
        profileTaskRepository.save(profileTask);

        return thingMapper.apply(originalThing);
    }

    @Override
    @Transactional
    public void deleteThing(Principal principal, Long id) {
        removeThingFromProfileTask("remove", principal, id);
    }

    @Override
    public void archiveThing(Principal principal, Long id) {
        removeThingFromProfileTask("archive", principal, id);
    }

    @Override
    public List<ThingDTO> getArchivedThings(Principal principal) {
        Profile profile = profileService.getProfileEntityByPrincipal(principal);
        return profile.getProfileTasks()
                .stream()
                .map(ProfileTask::getThings)
                .flatMap(Collection::stream)
                .filter(Thing::isArchived)
                .map(thingMapper)
                .toList();
    }

    private void removeThingFromProfileTask(String action, Principal principal, Long thingId) {
        Profile profile = profileService.getProfileEntityByPrincipal(principal);

        Thing thing = thingRepository
                .findById(thingId)
                .orElseThrow(() -> new EntityNotFoundException(Thing.class, thingId));

        if (thing.getProfileTask().getProfile().getId().equals(profile.getId())) {
            ProfileTask profileTask = thing.getProfileTask();
            profileTask.setThings(new ArrayList<>());
            profileTaskRepository.save(profileTask);
            switch (action) {
                case "remove" -> thing.setRemoved(true);
                case "archive" -> thing.setArchived(true);
            }
            thing = thingRepository.save(thing);
            log.info("Успешное удаление вещи [id: {}, removed: {}]", thing.getId(), thing.isRemoved());
        } else {
            throw new RuntimeException("Невозможно удалить чужую вещь");
        }
    }
}
