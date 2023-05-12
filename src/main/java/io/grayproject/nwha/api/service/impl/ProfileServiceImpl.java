package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.domain.Profile;
import io.grayproject.nwha.api.dto.ProfileDTO;
import io.grayproject.nwha.api.exception.EntityNotFoundException;
import io.grayproject.nwha.api.mapper.ProfileMapper;
import io.grayproject.nwha.api.repository.ProfileRepository;
import io.grayproject.nwha.api.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * @author Ilya Avkhimenya
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;

    @Override
    public ProfileDTO getProfileByPrincipal(Principal principal) {
        return profileMapper.apply(getProfileEntityByPrincipal(principal));
    }

    @Override
    public ProfileDTO getProfileById(Long id) {
        return profileMapper.apply(getProfileEntityById(id));
    }

    @Override
    public ProfileDTO getProfileByUsername(String username) {
        return profileRepository
                .findProfileByUserUsername(username)
                .map(profileMapper)
                .orElseThrow(() -> new EntityNotFoundException(username));
    }

    @Override
    public ProfileDTO updateProfileDescription(Principal principal, String description) {
        Profile profile = getProfileEntityByPrincipal(principal);
        profile.setDescription(description.equals("") ? null : description);
        return profileMapper.apply(profileRepository.save(profile));
    }

    @Override
    public ProfileDTO updateProfilePersonalLink(Principal principal, String personalLink) {
        Profile profile = getProfileEntityByPrincipal(principal);
        profile.setPersonalLink(personalLink.equals("") ? null : personalLink);
        return profileMapper.apply(profileRepository.save(profile));
    }

    @Override
    public Profile getProfileEntityById(Long id) {
        return profileRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

    }

    @Override
    public Profile getProfileEntityByPrincipal(Principal principal) {
        return profileRepository
                .findProfileByUserUsername(principal.getName())
                .orElseThrow(RuntimeException::new);
    }
}
