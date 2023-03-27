package io.grayproject.nwha.api.dto;

import lombok.Builder;

import java.util.Set;

/**
 * @author Ilya Avkhimenya
 */
@Builder
public record ProfileDTO(Long id,
                         String username,
                         String description,
                         Set<ProfileTaskDTO> profileTasks,
                         Set<ProfilePairTraitsDTO> profilePairsTraits) {
}