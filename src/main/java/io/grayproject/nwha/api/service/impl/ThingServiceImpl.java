package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.TelegramNotificationSender;
import io.grayproject.nwha.api.domain.Profile;
import io.grayproject.nwha.api.domain.ProfileTask;
import io.grayproject.nwha.api.domain.Thing;
import io.grayproject.nwha.api.dto.ProfileDTO;
import io.grayproject.nwha.api.dto.RecentlyThingDTO;
import io.grayproject.nwha.api.dto.ThingDTO;
import io.grayproject.nwha.api.exception.EntityNotFoundException;
import io.grayproject.nwha.api.mapper.ThingMapper;
import io.grayproject.nwha.api.repository.ProfileTaskRepository;
import io.grayproject.nwha.api.repository.ThingRepository;
import io.grayproject.nwha.api.service.ProfileService;
import io.grayproject.nwha.api.service.ThingService;
import io.grayproject.nwha.api.util.RandomThingOfDayScheduler;
import lombok.RequiredArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Ilya Avkhimenya
 */
@Service
@RequiredArgsConstructor
public class ThingServiceImpl implements ThingService {
    private final ThingMapper thingMapper;
    private final ProfileService profileService;
    private final ThingRepository thingRepository;
    private final ProfileTaskRepository profileTaskRepository;
    private final TelegramNotificationSender telegramNotificationSender;
    private final RandomThingOfDayScheduler randomThingOfDayScheduler;

    @Override
    public ThingDTO getThingById(Long id) {
        Thing thing = thingRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Thing.class, id));
        ThingDTO thingDTO = thingMapper.apply(thing);
        thingDTO.setAmountCollections(thingRepository.amountCollectionsByThingId(id));
        return thingDTO;
    }

    @Override
    public ThingDTO createThing(Principal principal, ThingDTO thingDTO) {
        Profile profile = profileService.getProfileEntityByPrincipal(principal);

        ProfileTask profileTask = profile
                .getProfileTasks()
                .stream()
                .filter(p -> p.getId().equals(thingDTO.getProfileTaskId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("It is impossible to create thing for the specified profile task"));

        profileTask
                .getThings()
                .stream()
                .filter(thing -> !thing.isRemoved() && !thing.isArchived())
                .findFirst()
                .ifPresent(thing -> {
                    throw new RuntimeException("It is not possible to create a new Thing because the task already contains an actual Thing");
                });

        // create actual thing
        Thing thing = Thing
                .builder()
                .removed(false)
                .archived(false)
                .description(thingDTO.getDescription())
                .profileTask(profileTask)
                .fileUrl(thingDTO.getPictureLink())
                .build();

        profileTask.getThings().add(thing);
        Thing saved = thingRepository.save(thing);

        String prettyTaskNumber = profileTask.getTask().getOrdinalNumber() > 10
                ? profileTask.getTask().getOrdinalNumber().toString()
                : "0" + profileTask.getTask().getOrdinalNumber();

        telegramNotificationSender.sendMessage(String.format("+ thing %s – %s by %s\n\n%s", saved.getId(), prettyTaskNumber, profile.getUser().getUsername(),
                thing.getDescription() != null && !thing.getDescription().isBlank() ? thing.getDescription() : ""));

        profileTaskRepository.save(profileTask);
        return getThingById(saved.getId());
    }

    @Override
    public ThingDTO updateThing(Principal principal, ThingDTO thingDTO) {
        Thing thing = thingRepository
                .findById(thingDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException(Thing.class, thingDTO.getId()));

        Long principalProfileId = profileService
                .getProfileEntityByPrincipal(principal).getId();

        if (!principalProfileId.equals(thing.getProfileTask().getProfile().getId())) {
            throw new RuntimeException("Sorry, but you cannot update this thing.");
        }

        Optional.of(thingDTO.getPictureLink())
                .ifPresent(thing::setFileUrl);
        Optional.ofNullable(thingDTO.getDescription())
                .ifPresent(thing::setDescription);
        Optional.of(thingDTO.isArchived())
                .ifPresent(thing::setArchived);
        Optional.of(thingDTO.isRemoved())
                .ifPresent(thing::setRemoved);

        Thing saved = thingRepository.save(thing);
        return getThingById(saved.getId());
    }

    @Override
    public List<ThingDTO> getArchivedThings(Principal principal) {
        ProfileDTO profileByPrincipal = profileService.getProfileByPrincipal(principal);
        return thingRepository
                .findArchivedThingsByProfileId(profileByPrincipal.id())
                .stream()
                .map(thingMapper)
                .toList();
    }

    @Override
    public List<RecentlyThingDTO> getRecentlyThings() {
        return thingRepository
                .findAll()
                .stream()
                .filter(t -> !t.isRemoved() && !t.isArchived())
                .sorted(Comparator.comparing(Thing::getCreatedAt).reversed())
                .map(t -> {
                    String prettyTimeString =
                            new PrettyTime(Locale.ENGLISH).format(t.getCreatedAt());
                    if (prettyTimeString.equals("moments ago")) {
                        prettyTimeString = "now";
                    }
                    return RecentlyThingDTO
                            .builder()
                            .prettyTime(prettyTimeString)
                            .thingId(t.getId())
                            .pictureLink(t.getFileUrl())
                            .taskOrdinalNumber(t.getProfileTask().getTask().getOrdinalNumber())
                            .username(t.getProfileTask().getProfile().getUser().getUsername())
                            .build();
                })
                .toList();
    }

    @Override
    public List<ThingDTO> getThingsByTaskId(Long taskId) {
        return thingRepository
                .findThingByTaskId(taskId)
                .stream()
                .filter(t -> !t.isArchived() && !t.isRemoved())
                .map(thingMapper)
                .toList();
    }

    @Override
    public ThingDTO getRandomThingOfDay() {
        Thing getRandomThingOfDay = randomThingOfDayScheduler.getGetRandomThingOfDay();
        return thingMapper.apply(getRandomThingOfDay);
    }
}
