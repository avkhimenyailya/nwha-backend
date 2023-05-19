package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.domain.Profile;
import io.grayproject.nwha.api.domain.ProfileTask;
import io.grayproject.nwha.api.domain.Thing;
import io.grayproject.nwha.api.dto.ThingDTO2;
import io.grayproject.nwha.api.exception.EntityNotFoundException;
import io.grayproject.nwha.api.mapper.ThingMapper2;
import io.grayproject.nwha.api.repository.ProfileTaskRepository;
import io.grayproject.nwha.api.repository.ThingRepository;
import io.grayproject.nwha.api.service.ProfileService;
import io.grayproject.nwha.api.service.ThingService2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * @author Ilya Avkhimenya
 */
@Service
@RequiredArgsConstructor
public class ThingService2Impl implements ThingService2 {
    private final ThingMapper2 thingMapper2;
    private final ProfileService profileService;
    private final ThingRepository thingRepository;
    private final ProfileTaskRepository profileTaskRepository;

    @Override
    public ThingDTO2 getThingById(Long id) {
        Thing thing = thingRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Thing.class, id));
        ThingDTO2 thingDTO2 = thingMapper2.apply(thing);
        thingDTO2.setAmountCollections(thingRepository.amountCollectionsByThingId(id));
        return thingDTO2;
    }

    @Override
    public ThingDTO2 createThing(Principal principal, ThingDTO2 thingDTO2) {
        Profile profile = profileService.getProfileEntityByPrincipal(principal);

        ProfileTask profileTask = profile
                .getProfileTasks()
                .stream()
                .filter(p -> p.getId().equals(thingDTO2.getProfileTaskId()))
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

        Thing thing = Thing
                .builder()
                .removed(false)
                .archived(false)
                .description(thingDTO2.getDescription())
                .profileTask(profileTask)
                .fileUrl(thingDTO2.getPictureLink())
                .build();

        profileTask.getThings().add(thing);
        Thing saved = thingRepository.save(thing);

        profileTaskRepository.save(profileTask);
        return getThingById(saved.getId());
    }

    @Override
    public ThingDTO2 updateThing(Principal principal, ThingDTO2 thingDTO2) {
        Thing thing = thingRepository
                .findById(thingDTO2.getId())
                .orElseThrow(() -> new EntityNotFoundException(Thing.class, thingDTO2.getId()));

        Long principalProfileId = profileService
                .getProfileEntityByPrincipal(principal).getId();

        if (!principalProfileId.equals(thing.getProfileTask().getProfile().getId())) {
            throw new RuntimeException("Sorry, but you cannot update this thing.");
        }

        Optional.ofNullable(thingDTO2.getPictureLink())
                .ifPresent(thing::setFileUrl);
        Optional.ofNullable(thingDTO2.getDescription())
                .ifPresent(thing::setDescription);
        Optional.of(thingDTO2.isArchived())
                .ifPresent(thing::setArchived);
        Optional.of(thingDTO2.isRemoved())
                .ifPresent(thing::setRemoved);

        Thing saved = thingRepository.save(thing);
        return getThingById(saved.getId());
    }

    @Override
    public List<ThingDTO2> getArchivedThings(Principal principal) {
        return null;
    }

    @Override
    public List<ThingDTO2> getThingsByTaskOrdinalNumber(Long taskOrdinalNumber) {
        List<Thing> thingByTaskOrdinalNumber = thingRepository.findThingByTaskOrdinalNumber(taskOrdinalNumber);
        return thingByTaskOrdinalNumber.stream().map(thingMapper2).toList();
    }
}
