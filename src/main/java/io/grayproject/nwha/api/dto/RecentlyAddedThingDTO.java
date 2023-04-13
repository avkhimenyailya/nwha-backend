package io.grayproject.nwha.api.dto;

import lombok.Builder;

/**
 * @author Ilya Avkhimenya
 */
@Builder
public record RecentlyAddedThingDTO(Long thingId,
                                    String thingFileUrl,
                                    String addingTime,
                                    String username,
                                    Integer taskOrdinalNumber,
                                    String taskDescription) {
}