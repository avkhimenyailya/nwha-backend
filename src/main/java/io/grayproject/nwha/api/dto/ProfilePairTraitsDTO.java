package io.grayproject.nwha.api.dto;

import lombok.Builder;

/**
 * @author Ilya Avkhimenya
 */
@Builder
public record ProfilePairTraitsDTO(ProfileTraitDTO firstProfileTrait,
                                   ProfileTraitDTO secondProfileTrait) {
}
