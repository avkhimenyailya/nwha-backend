package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.dto.ProfileDTO;
import io.grayproject.nwha.api.dto.ProfilePairTraitsDTO;
import io.grayproject.nwha.api.dto.ProfileTaskDTO;
import io.grayproject.nwha.api.dto.ProfileTraitDTO;
import io.grayproject.nwha.api.entity.Profile;
import io.grayproject.nwha.api.entity.ProfileTask;
import io.grayproject.nwha.api.entity.ProfileTrait;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ProfileMapper implements Function<Profile, ProfileDTO> {
    private final ProfileTaskMapper profileTaskMapper;
    private final ProfileTraitMapper profileTraitMapper;

    @Override
    public ProfileDTO apply(Profile profile) {
        return ProfileDTO
                .builder()
                .id(profile.getId())
                .username(profile.getUser().getUsername())
                .description(profile.getDescription())
                .profileTasks(getProfileTasks(profile.getProfileTasks()))
                .profilePairsTraits(getProfilePairsTraits(profile.getProfileTraits()))
                .build();
    }

    private List<ProfileTaskDTO> getProfileTasks(List<ProfileTask> profileTasks) {
        return profileTasks
                .stream()
                .map(profileTaskMapper)
                .sorted(Comparator.comparing(ProfileTaskDTO::taskId))
                .toList();
    }

    private List<ProfilePairTraitsDTO> getProfilePairsTraits(List<ProfileTrait> profileTraits) {
        final Long[][] pairTraitIds = {
                {1L, 2L},
                {3L, 4L},
                {5L, 6L},
                {7L, 8L},
                {9L, 10L}
        };

        return Arrays.stream(pairTraitIds)
                .map(pair -> {
                    ProfileTraitDTO firstProfileTrait = profileTraits
                            .stream()
                            .filter(profileTrait -> profileTrait.getTrait().getId().equals(pair[0]))
                            .findFirst()
                            .map(profileTraitMapper)
                            .orElse(null);

                    ProfileTraitDTO secondProfileTrait = profileTraits
                            .stream()
                            .filter(profileTrait -> profileTrait.getTrait().getId().equals(pair[1]))
                            .findFirst()
                            .map(profileTraitMapper)
                            .orElse(null);

                    return ProfilePairTraitsDTO
                            .builder()
                            .firstProfileTrait(firstProfileTrait)
                            .secondProfileTrait(secondProfileTrait)
                            .build();
                })
                .toList();
    }
}
