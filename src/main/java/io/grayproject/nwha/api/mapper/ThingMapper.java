package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.domain.Thing;
import io.grayproject.nwha.api.dto.ThingDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author Ilya Avkhimenya
 */
@Component
public class ThingMapper implements Function<Thing, ThingDTO> {

    @Override
    public ThingDTO apply(Thing thing) {
        return ThingDTO
                .builder()
                .id(thing.getId())
                .profileId(thing.getProfileTask().getProfile().getId())
                .archived(thing.isArchived())
                .description(thing.getDescription())
                .profileTaskId(thing.getProfileTask().getId())
                .fileUrl(thing.getFileUrl())
                .build();
    }
}
