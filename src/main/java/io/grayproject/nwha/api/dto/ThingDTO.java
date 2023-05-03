package io.grayproject.nwha.api.dto;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record ThingDTO(Long id,
                       Long profileId,
                       String fileUrl,
                       String description,
                       Integer taskOrdinalNumber,
                       @NonNull Long profileTaskId,
                       Boolean archived,
                       String addedDate) {
}
