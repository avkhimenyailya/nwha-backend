package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.domain.CollectionThings;
import io.grayproject.nwha.api.dto.CollectionThingsDTO;
import io.grayproject.nwha.api.dto.ThingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.function.Function;

/**
 * @author Ilya Avkhimenya
 */
@Component
public class CollectionThingsMapper implements Function<CollectionThings, CollectionThingsDTO> {
    private final ThingMapper thingMapper;

    @Autowired
    public CollectionThingsMapper(ThingMapper thingMapper) {
        this.thingMapper = thingMapper;
    }

    @Override
    public CollectionThingsDTO apply(CollectionThings collectionThings) {
        return CollectionThingsDTO
                .builder()
                .id(collectionThings.getId())
                .profileId(collectionThings.getProfile().getId())
                .name(collectionThings.getName())
                .things(collectionThings
                        .getThings()
                        .stream()
                        .map(thingMapper)
                        .sorted(Comparator.comparing(ThingDTO::getId).reversed())
                        .toList())
                .build();
    }
}
