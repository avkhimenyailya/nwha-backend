package io.grayproject.nwha.api.service;

import io.grayproject.nwha.api.dto.CollectionThingsDTO;
import io.grayproject.nwha.api.dto.ProfileDTO;
import io.grayproject.nwha.api.dto.ThingDTO;
import io.grayproject.nwha.api.exception.ProfileNotFoundException;

import java.security.Principal;
import java.util.List;

public interface ProfileService {

    ProfileDTO getProfile(Principal principal);

    ProfileDTO getProfileById(Long profileId) throws ProfileNotFoundException;

    List<ThingDTO> getProfileThings(Principal principal);

    List<ThingDTO> getProfileArchivedThings(Principal principal, boolean archive);

    List<ThingDTO> getProfileThingsByProfileId(Long profileId);

    List<CollectionThingsDTO> getProfileCollectionsThings(Principal principal);

    List<CollectionThingsDTO> getProfileCollectionsThingsByProfileId(Long profileId);

    ProfileDTO updateProfile(ProfileDTO profileDTO);
}
