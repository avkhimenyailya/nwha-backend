package io.grayproject.nwha.api.controller.final_pkg;

import io.grayproject.nwha.api.dto.CollectionThingsDTO;
import io.grayproject.nwha.api.dto.ProfileDTO;
import io.grayproject.nwha.api.dto.ThingDTO;
import io.grayproject.nwha.api.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static io.grayproject.nwha.api.util.ControllerPaths.ProfileControllerPaths.*;

/**
 * @author Ilya Avkhimenya
 */
@RestController
@RequestMapping(CONTROLLER_PATH)
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(GET_PROFILE)
    public ProfileDTO getProfile(Principal principal) {
        return profileService.getProfile(principal);
    }

    @GetMapping(GET_PROFILE_BY_ID)
    public ProfileDTO getProfileById(@PathVariable Long id) {
        return profileService.getProfileById(id);
    }

    @GetMapping(GET_PROFILE_BY_USERNAME)
    public ProfileDTO getProfileByUsername(@PathVariable String username) {
        return profileService.getProfileByUsername(username);
    }

    @GetMapping(GET_ALL_THINGS)
    public List<ThingDTO> getProfileThings(Principal principal,
                                           @RequestParam(required = false) Boolean archive) {
        if (archive == null) archive = false;
        return profileService.getProfileThings(principal, archive);
    }

    @GetMapping(GET_ALL_THINGS_BY_PROFILE_ID)
    public List<ThingDTO> getProfileThingsByProfileId(@PathVariable Long id) {
        return profileService.getProfileThingsByProfileId(id);
    }

    @GetMapping(GET_ALL_COLLECTIONS)
    public List<CollectionThingsDTO> getProfileCollectionsThings(Principal principal) {
        return profileService.getProfileCollectionsThings(principal);
    }

    @GetMapping(GET_ALL_COLLECTIONS_BY_PROFILE_ID)
    public List<CollectionThingsDTO> getProfileCollectionsThingsByProfileId(@PathVariable Long id) {
        return profileService.getProfileCollectionsThingsByProfileId(id);
    }

    @PutMapping(PUT_UPDATE_PROFILE_BY_DTO)
    public ProfileDTO updateProfile(@RequestBody ProfileDTO profileDTO) {
        return profileService.updateProfile(profileDTO);
    }
}
