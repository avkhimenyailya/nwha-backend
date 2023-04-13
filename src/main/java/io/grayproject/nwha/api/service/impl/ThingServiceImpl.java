package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.domain.Profile;
import io.grayproject.nwha.api.domain.ProfileTask;
import io.grayproject.nwha.api.domain.Thing;
import io.grayproject.nwha.api.dto.LastThingDTO;
import io.grayproject.nwha.api.dto.ThingDTO;
import io.grayproject.nwha.api.exception.EntityNotFoundException;
import io.grayproject.nwha.api.mapper.ThingMapper;
import io.grayproject.nwha.api.repository.ProfileRepository;
import io.grayproject.nwha.api.repository.ProfileTaskRepository;
import io.grayproject.nwha.api.repository.ThingRepository;
import io.grayproject.nwha.api.service.ThingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.*;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ThingServiceImpl implements ThingService {
    private final ProfileRepository profileRepository;

    private final ThingMapper thingMapper;
    private final ThingRepository thingRepository;
    private final ProfileTaskRepository profileTaskRepository;

    // use only profileService
    public List<ThingDTO> getAllThingsByProfileId(Long profileId) {
        return profileRepository.findById(profileId)
                .map(Profile::getProfileTasks)
                .map(profileTasks -> profileTasks
                        .stream()
                        .map(ProfileTask::getThing)
                        .filter(Objects::nonNull)
                        .map(thingMapper)
                        .toList())
                // fatal error (this should not be)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<ThingDTO> getRandomThings(Integer limit) {
        List<Thing> all = thingRepository.findAll();
        Collections.shuffle(all);
        return all.stream()
                .map(thingMapper)
                .limit(limit)
                .toList();
    }

    @Override
    public List<LastThingDTO> getLastThingsLimit80() {
        List<Thing> recentlyAddedLimit80 = thingRepository.findAll();
        List<LastThingDTO> list = new ArrayList<>(recentlyAddedLimit80
                .stream()
                .map(thing -> LastThingDTO
                        .builder()
                        .thingId(thing.getId())
                        .taskOrdinalNumber(thing.getProfileTask().getTask().getOrdinalNumber())
                        .taskDescription(thing.getProfileTask().getTask().getDescription())
                        .username(thing.getProfileTask().getProfile().getUser().getUsername())
                        .addingTime(new PrettyTime(Locale.ENGLISH).format(thing.getCreatedAt()))
                        .build())
                .toList());
        Collections.reverse(list);
        return list;
    }

    @Override
    public ThingDTO getThingById(Long id) {
        return thingRepository
                .findById(id)
                .map(thingMapper)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public ThingDTO createThing(Principal principal, ThingDTO thingDTO) {
        Profile profile = getProfileByPrincipal(principal)
                // fatal error (this should not be)
                .orElseThrow(RuntimeException::new);

        ProfileTask profileTask = profile
                .getProfileTasks()
                .stream()
                .filter(p -> p.getId().equals(thingDTO.profileTaskId()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Невозможно создать вещь для указанного profileTaskId"));

        Thing thing = Thing
                .builder()
                .removed(false)
                .archived(false)
                .description(thingDTO.description())
                .profileTask(profileTask)
                .fileUrl(thingDTO.fileUrl())
                .build();

        profileTask.setThing(thing);
        Thing saved = thingRepository.save(thing);
        profileTaskRepository.save(profileTask);
        return thingMapper.apply(saved);
    }

    @Override
    public ThingDTO updateThing(Principal principal, ThingDTO thingDTO) {
        return null;
    }

    @Override
    public void deleteThing(Principal principal, Long id) {

    }

    @Override
    @Transactional
    public ThingDTO setImageUrl(Principal principal,
                                MultipartFile multipartFile,
                                String thingId) throws IOException {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        String generatedName
                = RandomStringUtils.random(40, true, false) + "." + extension;
        File file = new File("images/" + generatedName);
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }

        return thingRepository
                .findById(Long.parseLong(thingId))
                .map(thing -> {
                    thing.setFileUrl("https://api.nwha.grayproject.io/img/"
                            + generatedName);
                    thingRepository.save(thing);
                    return thingMapper.apply(thing);
                })
                .orElseThrow(() ->
                        new EntityNotFoundException(Long.parseLong(thingId)));
    }

    private Optional<Profile> getProfileByPrincipal(Principal principal) {
        return profileRepository
                .findProfileByUserUsername(principal.getName());
    }
}
