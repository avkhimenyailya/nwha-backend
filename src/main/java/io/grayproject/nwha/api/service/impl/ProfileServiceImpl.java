package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.domain.Profile;
import io.grayproject.nwha.api.dto.CollectionThingsDTO;
import io.grayproject.nwha.api.dto.ProfileDTO;
import io.grayproject.nwha.api.dto.ThingDTO;
import io.grayproject.nwha.api.exception.EntityNotFoundException;
import io.grayproject.nwha.api.mapper.ProfileMapper;
import io.grayproject.nwha.api.repository.ProfileRepository;
import io.grayproject.nwha.api.service.ProfileService;
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
public class ProfileServiceImpl implements ProfileService {
    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;

    private final ThingServiceImpl thingService;
    private final CollectionThingsServiceImpl collectionThingsService;

    @Override
    public ProfileDTO getProfile(Principal principal) {
        return getProfileByPrincipal(principal)
                .map(profileMapper)
                // fatal error (this should not be)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public ProfileDTO getProfileById(Long id) {
        return profileRepository
                .findById(id)
                .map(profileMapper)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public ProfileDTO getProfileByUsername(String username) {
        return profileRepository
                .findProfileByUserUsername(username)
                .map(profileMapper)
                .orElseThrow(() -> new EntityNotFoundException(username)) ;
    }

    @Override
    public List<ThingDTO> getProfileThings(Principal principal, Boolean archive) {
        // todo filter archive!
        return getProfileByPrincipal(principal)
                .map(Profile::getId)
                .map(thingService::getAllThingsByProfileId)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<CollectionThingsDTO> getProfileCollectionsThings(Principal principal) {
        return getProfileByPrincipal(principal)
                .map(Profile::getId)
                .map(collectionThingsService::getAllCollectionThingsByProfileId)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<ThingDTO> getProfileThingsByProfileId(Long id) {
        return thingService.getAllThingsByProfileId(id);
    }

    @Override
    public List<CollectionThingsDTO> getProfileCollectionsThingsByProfileId(Long id) {
        return collectionThingsService.getAllCollectionThingsByProfileId(id);
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) {
        Profile profile = profileRepository
                .findById(profileDTO.id())
                .orElseThrow(() -> new EntityNotFoundException(profileDTO.id()));

        String newDescription = profileDTO.description();
        profile.setDescription(newDescription);

        return profileMapper
                .apply(profileRepository.save(profile));
    }

    private Optional<Profile> getProfileByPrincipal(Principal principal) {
        return profileRepository
                .findProfileByUserUsername(principal.getName());
    }
}
