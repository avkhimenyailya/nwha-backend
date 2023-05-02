package io.grayproject.nwha.api.dto;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record ThingDTO(Long id,
                       String description,
                       Boolean archived,
                       String fileUrl,
                       Long profileId,
                       String addedDate,
                       @NonNull Long profileTaskId) {
}
