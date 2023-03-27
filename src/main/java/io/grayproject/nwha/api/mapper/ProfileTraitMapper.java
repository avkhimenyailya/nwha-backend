package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.dto.ProfileTraitDTO;
import io.grayproject.nwha.api.entity.ProfileTrait;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProfileTraitMapper implements Function<ProfileTrait, ProfileTraitDTO> {

    @Override
    public ProfileTraitDTO apply(ProfileTrait profileTrait) {
        return ProfileTraitDTO
                .builder()
                .traitId(profileTrait.getTrait().getId())
                .profileId(profileTrait.getProfile().getId())
                .value(profileTrait.getValue())
                .build();
    }
}
