package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.dto.CollectionThingsDTO;
import io.grayproject.nwha.api.domain.CollectionThings;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author Ilya Avkhimenya
 */
@Component
public class CollectionThingsMapper implements Function<CollectionThings, CollectionThingsDTO> {

    // todo
    @Override
    public CollectionThingsDTO apply(CollectionThings collectionThings) {
        return CollectionThingsDTO
                .builder()
                .build();
    }
}
