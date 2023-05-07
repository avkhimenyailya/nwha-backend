package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.domain.ProfileAttribute;
import io.grayproject.nwha.api.dto.ProfileAttributeDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProfileAttributeMapper implements Function<ProfileAttribute, ProfileAttributeDTO> {

    @Override
    public ProfileAttributeDTO apply(ProfileAttribute profileAttribute) {
        return ProfileAttributeDTO
                .builder()
                .attributeName(profileAttribute.getAttribute().getName())
                .profileId(profileAttribute.getProfile().getId())
                .value(profileAttribute.getValue())
                .build();
    }
}
