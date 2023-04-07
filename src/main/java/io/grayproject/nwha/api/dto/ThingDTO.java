package io.grayproject.nwha.api.dto;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record ThingDTO(Long id,
                       String description,
                       Boolean archived,
                       @NonNull Long profileTaskId,
                       @NonNull String fileUrl) {
}
