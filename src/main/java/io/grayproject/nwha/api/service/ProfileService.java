package io.grayproject.nwha.api.service;

import io.grayproject.nwha.api.dto.CollectionThingsDTO;
import io.grayproject.nwha.api.dto.ProfileDTO;
import io.grayproject.nwha.api.dto.ThingDTO;

import java.security.Principal;
import java.util.List;

public interface ProfileService {

    ProfileDTO getProfile(Principal principal);

    ProfileDTO getProfileById(Long id);

    ProfileDTO getProfileByUsername(String username);

    List<ThingDTO> getProfileThings(Principal principal, Boolean archive);

    List<CollectionThingsDTO> getProfileCollectionsThings(Principal principal);

    List<ThingDTO> getProfileThingsByProfileId(Long id);

    List<CollectionThingsDTO> getProfileCollectionsThingsByProfileId(Long id);

    ProfileDTO updateProfile(ProfileDTO profileDTO);
}
