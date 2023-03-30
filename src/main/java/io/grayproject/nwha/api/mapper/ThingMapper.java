package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.dto.ThingDTO;
import io.grayproject.nwha.api.entity.Thing;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author Ilya Avkhimenya
 */
@Component
public class ThingMapper implements Function<Thing, ThingDTO> {

    // todo
    @Override
    public ThingDTO apply(Thing thing) {
        return ThingDTO
                .builder()
                .build();
    }
}
