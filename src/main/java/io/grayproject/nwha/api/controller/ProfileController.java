package io.grayproject.nwha.api.controller;

import io.grayproject.nwha.api.dto.CollectionThingsDTO;
import io.grayproject.nwha.api.dto.ProfileDTO;
import io.grayproject.nwha.api.dto.ProfileTaskDTO;
import io.grayproject.nwha.api.dto.ThingDTO;
import io.grayproject.nwha.api.service.CollectionThingsService;
import io.grayproject.nwha.api.service.ProfileService;
import io.grayproject.nwha.api.service.ProfileTaskService;
import io.grayproject.nwha.api.service.ThingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final ProfileTaskService profileTaskService;
    private final CollectionThingsService collectionThingsService;
    private final ThingService thingService;

    @GetMapping
    public ResponseEntity<ProfileDTO> getProfile(Principal principal,
                                                 HttpServletRequest httpServletRequest) {
        log.info("Request to get profile by principal ({}), ip: {}", principal.getName(), httpServletRequest.getHeader("X-Real-IP"));
        ProfileDTO profileDto = profileService.getProfileByPrincipal(principal);
        return ResponseEntity.ok(profileDto);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable Long id,
                                                     HttpServletRequest httpServletRequest) {
        log.info("Request to get profile by id: {}, ip: {}", id, httpServletRequest.getHeader("X-Real-IP"));
        ProfileDTO profileDto = profileService.getProfileById(id);
        return ResponseEntity.ok(profileDto);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ProfileDTO> getProfileByUsername(@PathVariable String username,
                                                           HttpServletRequest httpServletRequest) {
        log.info("Request to get profile by username: {}, ip: {}",
                username,
                httpServletRequest.getHeader("X-Real-IP"));
        ProfileDTO profileDto = profileService.getProfileByUsername(username);
        return ResponseEntity.ok(profileDto);
    }

    @PutMapping("/description")
    public ResponseEntity<ProfileDTO> updateProfileDescriptionByPrincipal(Principal principal,
                                                                          HttpServletRequest httpServletRequest,
                                                                          @RequestParam @Validated @Max(200) String description) {
        log.info("Request to update profile description by principal ({}), ip: {}",
                principal.getName(),
                httpServletRequest.getHeader("X-Real-IP"));
        ProfileDTO profileDto = profileService.updateProfileDescription(principal, description);
        return ResponseEntity.ok(profileDto);
    }

    @PutMapping("/personalLink")
    public ResponseEntity<ProfileDTO> updateProfilePersonalLinkByPrincipal(Principal principal,
                                                                          HttpServletRequest httpServletRequest,
                                                                          @RequestParam @Validated @Max(200) String personalLink) {
        log.info("Request to update profile personal link by principal ({}), ip: {}",
                principal.getName(),
                httpServletRequest.getHeader("X-Real-IP"));
        ProfileDTO profileDto = profileService.updateProfilePersonalLink(principal, personalLink);
        return ResponseEntity.ok(profileDto);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<ProfileTaskDTO>> getProfileTasksByPrincipal(Principal principal,
                                                                           HttpServletRequest httpServletRequest) {
        log.info("Request to get profile task by principal ({}), ip: {}",
                principal.getName(),
                httpServletRequest.getHeader("X-Real-IP"));
        return ResponseEntity.ok(profileTaskService.getProfileTasksByPrincipal(principal));
    }

    @GetMapping("/{profileId}/tasks")
    public ResponseEntity<List<ProfileTaskDTO>> getProfileTasksById(@PathVariable Long profileId,
                                                                    HttpServletRequest httpServletRequest) {
        log.info("Request to get profile task by id: {}, ip: {}",
                profileId,
                httpServletRequest.getHeader("X-Real-IP"));
        return ResponseEntity.ok(profileTaskService.getProfileTasksByProfileId(profileId));
    }

    @GetMapping("/collectionsThings")
    public ResponseEntity<List<CollectionThingsDTO>> getProfileCollectionByPrincipal(Principal principal,
                                                                                     HttpServletRequest httpServletRequest) {
        log.info("Request to get collection things by principal ({}), ip: {}",
                principal.getName(),
                httpServletRequest.getHeader("X-Real-IP"));
        return ResponseEntity.ok(collectionThingsService.getCollectionThingsPrincipal(principal));
    }

    @GetMapping("/{profileId}/collectionsThings")
    public ResponseEntity<List<CollectionThingsDTO>> getProfileCollectionById(@PathVariable Long profileId,
                                                                              HttpServletRequest httpServletRequest) {
        log.info("Request to get collection things by id: {}, ip: {}",
                profileId,
                httpServletRequest.getHeader("X-Real-IP"));
        return ResponseEntity.ok(collectionThingsService.getCollectionThingsByProfileId(profileId));
    }

    @GetMapping("/archivedThings")
    public ResponseEntity<List<ThingDTO>> getArchivedThingsByPrincipal(Principal principal,
                                                                       HttpServletRequest httpServletRequest) {
        log.info("Request to get archived things by principal ({}), ip: {}",
                principal.getName(),
                httpServletRequest.getHeader("X-Real-IP"));
        return ResponseEntity.ok(thingService.getArchivedThings(principal));
    }
}


