package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.dto.CollectionThingsDTO;
import io.grayproject.nwha.api.dto.ProfileDTO;
import io.grayproject.nwha.api.dto.ThingDTO;
import io.grayproject.nwha.api.exception.ProfileNotFoundException;
import io.grayproject.nwha.api.mapper.ProfileMapper;
import io.grayproject.nwha.api.repository.ProfileRepository;
import io.grayproject.nwha.api.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public ProfileDTO getProfile(Principal principal) {
        return null;
    }

    @Override
    public ProfileDTO getProfileById(Long profileId) throws ProfileNotFoundException {
        return profileRepository
                .findById(profileId)
                .map(profileMapper)
                .orElseThrow(() -> new ProfileNotFoundException(profileId));
    }

    @Override
    public List<ThingDTO> getProfileThings(Principal principal) {
        return null;
    }

    @Override
    public List<ThingDTO> getProfileArchivedThings(Principal principal, boolean archive) {
        return null;
    }

    @Override
    public List<ThingDTO> getProfileThingsByProfileId(Long profileId) {
        return null;
    }

    @Override
    public List<CollectionThingsDTO> getProfileCollectionsThings(Principal principal) {
        return null;
    }

    @Override
    public List<CollectionThingsDTO> getProfileCollectionsThingsByProfileId(Long profileId) {
        return null;
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) {
        return null;
    }
}
