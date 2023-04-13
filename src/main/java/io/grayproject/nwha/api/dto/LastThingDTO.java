package io.grayproject.nwha.api.dto;

import lombok.Builder;

/**
 * @author Ilya Avkhimenya
 */
@Builder
public record LastThingDTO(Long thingId,
                           String username,
                           Integer taskOrdinalNumber,
                           String taskDescription,
                           String addingTime) {
}