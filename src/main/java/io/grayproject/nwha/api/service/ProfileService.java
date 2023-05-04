package io.grayproject.nwha.api.service;

import io.grayproject.nwha.api.domain.Profile;
import io.grayproject.nwha.api.dto.ProfileDTO;

import java.security.Principal;

public interface ProfileService {

    ProfileDTO getProfileById(Long id);

    ProfileDTO getProfileByPrincipal(Principal principal);

    ProfileDTO getProfileByUsername(String username);

    ProfileDTO updateProfileDescription(Principal principal, String description);

    Profile getProfileEntityById(Long id);

    Profile getProfileEntityByPrincipal(Principal principal);
}
