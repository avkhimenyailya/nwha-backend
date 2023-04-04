package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.domain.Profile;
import io.grayproject.nwha.api.domain.ProfileTask;
import io.grayproject.nwha.api.dto.ThingDTO;
import io.grayproject.nwha.api.mapper.ThingMapper;
import io.grayproject.nwha.api.repository.ProfileRepository;
import io.grayproject.nwha.api.repository.ThingRepository;
import io.grayproject.nwha.api.service.ThingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Ilya Avkhimenya
 */
@Service
@RequiredArgsConstructor
public class ThingServiceImpl implements ThingService {
    private final ProfileRepository profileRepository;

    private final ThingMapper thingMapper;
    private final ThingRepository thingRepository;

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
}
