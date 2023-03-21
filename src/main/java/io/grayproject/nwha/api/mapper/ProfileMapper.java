package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.dto.ProfileDTO;
import io.grayproject.nwha.api.entity.Profile;

import java.util.function.Function;

public class ProfileMapper implements Function<Profile, ProfileDTO> {

    @Override
    public ProfileDTO apply(Profile profile) {
        return ProfileDTO
                .builder()
                .description(profile.getDescription())
                .build();
    }
}
