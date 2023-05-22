package io.grayproject.nwha.api.dto;

import lombok.Builder;

/**
 * @author Ilya Avkhimenya
 */
@Builder
public record RecentlyThingDTO(Long thingId,
                               Integer taskOrdinalNumber,
                               String pictureLink,
                               String prettyTime,
                               String username,
                               ThingDTO thing) {
}