package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.domain.CollectionThings;
import io.grayproject.nwha.api.domain.Profile;
import io.grayproject.nwha.api.domain.Thing;
import io.grayproject.nwha.api.dto.CollectionThingsDTO;
import io.grayproject.nwha.api.exception.EntityNotFoundException;
import io.grayproject.nwha.api.mapper.CollectionThingsMapper;
import io.grayproject.nwha.api.repository.CollectionThingsRepository;
import io.grayproject.nwha.api.repository.ThingRepository;
import io.grayproject.nwha.api.service.CollectionThingsService;
import io.grayproject.nwha.api.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Ilya Avkhimenya
 */
@Service
@RequiredArgsConstructor
public class CollectionThingsServiceImpl implements CollectionThingsService {
    private final ProfileService profileService;

    private final CollectionThingsMapper collectionThingsMapper;
    private final CollectionThingsRepository collectionThingsRepository;

    private final ThingRepository thingRepository;

    @Override
    public Integer countCollectionsByThingId(Long thingId) {
        return collectionThingsRepository.countCollectionsByThingId(thingId);
    }

    @Override
    public CollectionThingsDTO getCollectionThingsById(Long id) {
        CollectionThings collectionThings = collectionThingsRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        return collectionThingsMapper.apply(collectionThings);
    }

    @Override
    public CollectionThingsDTO createCollectionThings(Principal principal, String name) {
        Profile profile = profileService.getProfileEntityByPrincipal(principal);

        CollectionThings collectionThings = CollectionThings
                .builder()
                .name(name)
                .profile(profile)
                .removed(false)
                .build();

        return collectionThingsMapper
                .apply(collectionThingsRepository.save(collectionThings));
    }

    @Override
    public void deleteCollectionThings(Principal principal, Long id) {
        CollectionThings collectionThings
                = getCollectionThingsByPrincipalAndId(principal, id);
        collectionThings.setRemoved(true);
        collectionThingsRepository.save(collectionThings);
    }

    @Override
    public CollectionThingsDTO updateCollectionThingsName(Principal principal, Long id, String name) {
        CollectionThings collectionThings = getCollectionThingsByPrincipalAndId(principal, id);
        collectionThings.setName(name);
        return collectionThingsMapper.apply(collectionThingsRepository.save(collectionThings));
    }

    @Override
    public CollectionThingsDTO putThingToCollectionThings(Principal principal, Long collectionId, Long thingId) {
        CollectionThings collectionThings = getCollectionThingsByPrincipalAndId(principal, collectionId);

        Thing thing = thingRepository
                .findById(thingId)
                .orElseThrow(() -> new EntityNotFoundException(Thing.class, thingId));

        Set<Thing> things = Optional
                .ofNullable(collectionThings.getThings())
                .orElse(new HashSet<>());
        things.add(thing);

        collectionThings.setThings(things);
        return collectionThingsMapper.apply(collectionThingsRepository.save(collectionThings));
    }

    @Override
    public CollectionThingsDTO removeThingFromCollectionThings(Principal principal, Long collectionId, Long thingId) {
        CollectionThings collectionThings = getCollectionThingsByPrincipalAndId(principal, collectionId);

        Thing thing = thingRepository
                .findById(thingId)
                .orElseThrow(() -> new EntityNotFoundException(Thing.class, thingId));

        Set<Thing> things = Optional.ofNullable(collectionThings.getThings()).orElse(new HashSet<>());
        things.removeIf(t -> t.equals(thing));

        collectionThings.setThings(things);
        CollectionThings save = collectionThingsRepository.save(collectionThings);
        return collectionThingsMapper.apply(save);
    }

    @Override
    public List<CollectionThingsDTO> getCollectionThingsPrincipal(Principal principal) {
        Profile profile = profileService.getProfileEntityByPrincipal(principal);
        return getCollectionThingsByProfileId(profile.getId());
    }

    @Override
    public List<CollectionThingsDTO> getCollectionThingsByProfileId(Long profileId) {
        return collectionThingsRepository
                .findAllByProfileId(profileId)
                .stream()
                .map(collectionThingsMapper)
                .toList();
    }

    private CollectionThings getCollectionThingsByPrincipalAndId(Principal principal, Long id) {
        Long principalProfileId = profileService.getProfileEntityByPrincipal(principal).getId();
        return collectionThingsRepository
                .findById(id)
                .stream()
                .filter(collectionThings -> collectionThings.getProfile().getId().equals(principalProfileId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sorry, but you cannot update this collection things."));
    }
}
