package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.domain.CollectionThings;
import io.grayproject.nwha.api.domain.Profile;
import io.grayproject.nwha.api.domain.Thing;
import io.grayproject.nwha.api.dto.CollectionThingsDTO;
import io.grayproject.nwha.api.exception.EntityNotFoundException;
import io.grayproject.nwha.api.mapper.CollectionThingsMapper;
import io.grayproject.nwha.api.repository.CollectionThingsRepository;
import io.grayproject.nwha.api.repository.ProfileRepository;
import io.grayproject.nwha.api.repository.ThingRepository;
import io.grayproject.nwha.api.service.CollectionThingsService;
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
    private final ProfileRepository profileRepository;

    private final CollectionThingsMapper collectionThingsMapper;
    private final CollectionThingsRepository collectionThingsRepository;

    private final ThingRepository thingRepository;

    @Override
    public CollectionThingsDTO getCollectionThingById(Long id) {
        // todo
        return null;
    }

    @Override
    public CollectionThingsDTO createCollectionThing(Principal principal, String name) {
        Profile profile = getProfileByPrincipal(principal)
                .orElseThrow(() -> new RuntimeException("Fatal error"));
        CollectionThings collectionThings = new CollectionThings();

        if (name.trim().length() < 6) {
            throw new RuntimeException("Имя коллекции слишком короткое (минимум 6 символов)");
        }
        collectionThings.setName(name.trim());
        collectionThings.setProfile(profile);

        CollectionThings save = collectionThingsRepository.save(collectionThings);
        return collectionThingsMapper.apply(save);
    }

    @Override
    public void deleteCollectionThing(Principal principal, Long id) {
        CollectionThings collectionThings
                = collectionThingsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));

        Profile profile = getProfileByPrincipal(principal)
                .orElseThrow(() -> new RuntimeException("Fatal error"));

        if (!collectionThings.getProfile().getId().equals(profile.getId())) {
            throw new RuntimeException("Нельзя удалить чужую коллекцию");
        }

        collectionThings.setRemoved(true);
        collectionThingsRepository.save(collectionThings);
    }

    @Override
    public CollectionThingsDTO updateCollectionThingName(Principal principal, Long id, String name) {
        CollectionThings collectionThings
                = collectionThingsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));

        Profile profile = getProfileByPrincipal(principal)
                .orElseThrow(() -> new RuntimeException("Fatal error"));

        if (!collectionThings.getProfile().getId().equals(profile.getId())) {
            throw new RuntimeException("Нельзя обновить чужую коллекцию");
        }

        if (name.trim().length() < 6) {
            throw new RuntimeException("Имя коллекции слишком короткое (минимум 6 символов)");
        }
        collectionThings.setName(name.trim());

        CollectionThings save = collectionThingsRepository.save(collectionThings);
        return collectionThingsMapper.apply(save);
    }

    @Override
    public CollectionThingsDTO putThingToCollectionThing(Principal principal, Long collectionId, Long thingId) {
        CollectionThings collectionThings
                = collectionThingsRepository.findById(collectionId).orElseThrow(() -> new EntityNotFoundException(collectionId));

        Profile profile = getProfileByPrincipal(principal)
                .orElseThrow(() -> new RuntimeException("Fatal error"));

        if (!collectionThings.getProfile().getId().equals(profile.getId())) {
            throw new RuntimeException("Нельзя добавить вещь в чужую коллекцию");
        }

        Thing thing = thingRepository
                .findById(thingId)
                .orElseThrow(() -> new EntityNotFoundException(thingId));

        Set<Thing> things = Optional
                .ofNullable(collectionThings.getThings())
                .orElse(new HashSet<>());
        things.add(thing);
        collectionThings.setThings(things);
        CollectionThings save = collectionThingsRepository.save(collectionThings);
        return collectionThingsMapper.apply(save);
    }

    @Override
    public CollectionThingsDTO removeThingFromCollectionThing(Principal principal, Long collectionId, Long thingId) {
        CollectionThings collectionThings
                = collectionThingsRepository.findById(collectionId).orElseThrow(() -> new EntityNotFoundException(collectionId));

        Profile profile = getProfileByPrincipal(principal)
                .orElseThrow(() -> new RuntimeException("Fatal error"));

        if (!collectionThings.getProfile().getId().equals(profile.getId())) {
            throw new RuntimeException("Нельзя удалить вещь из чужой коллекции");
        }

        Thing thing = thingRepository
                .findById(thingId)
                .orElseThrow(() -> new EntityNotFoundException(thingId));

        Set<Thing> things = Optional.ofNullable(collectionThings.getThings()).orElse(new HashSet<>());
        things.removeIf(t -> t.equals(thing));

        collectionThings.setThings(things);
        CollectionThings save = collectionThingsRepository.save(collectionThings);
        return collectionThingsMapper.apply(save);
    }

    /**
     * Для внутреннего использования (точно будет использоваться в ProfileServiceImpl)
     *
     * @return возвращает коллекции, которые принадлежат профилю с переданным id
     */
    List<CollectionThingsDTO> getAllCollectionThingsByProfileId(Long profileId) {
        return collectionThingsRepository
                .findAllByProfileId(profileId)
                .stream()
                .filter(collectionThings -> !collectionThings.getRemoved())
                .map(collectionThingsMapper)
                .toList();
    }

    private Optional<Profile> getProfileByPrincipal(Principal principal) {
        return profileRepository
                .findProfileByUserUsername(principal.getName());
    }
}
