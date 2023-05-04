package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.domain.Profile;
import io.grayproject.nwha.api.domain.ProfileAttribute;
import io.grayproject.nwha.api.dto.ProfileAttributeDTO;
import io.grayproject.nwha.api.dto.ProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ProfileMapper implements Function<Profile, ProfileDTO> {
    private final ProfileAttributeMapper profileAttributeMapper;

    @Override
    public ProfileDTO apply(Profile profile) {
        return ProfileDTO
                .builder()
                .id(profile.getId())
                .username(profile.getUser().getUsername())
                .description(profile.getDescription())
                .profileAttributes(getProfileAttributes(profile.getProfileAttributes()))
                .build();
    }

    private List<ProfileAttributeDTO> getProfileAttributes(List<ProfileAttribute> profileAttributes) {
        return profileAttributes.stream().map(profileAttributeMapper).toList();
    }
}
