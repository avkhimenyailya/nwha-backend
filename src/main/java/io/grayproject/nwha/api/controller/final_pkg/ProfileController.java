package io.grayproject.nwha.api.controller.final_pkg;

import io.grayproject.nwha.api.dto.ProfileDTO;
import io.grayproject.nwha.api.exception.ProfileNotFoundException;
import io.grayproject.nwha.api.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ilya Avkhimenya
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{profileId}")
    public ProfileDTO getProfileById(@PathVariable Long profileId)
            throws ProfileNotFoundException {
        return profileService.getProfileById(profileId);
    }
}
