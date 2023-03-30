package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.dto.CollectionThingsDTO;
import io.grayproject.nwha.api.mapper.CollectionThingsMapper;
import io.grayproject.nwha.api.repository.CollectionThingsRepository;
import io.grayproject.nwha.api.repository.ProfileRepository;
import io.grayproject.nwha.api.service.CollectionThingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@Service
@RequiredArgsConstructor
public class CollectionThingsServiceImpl implements CollectionThingsService {
    private final ProfileRepository profileRepository;

    private final CollectionThingsMapper collectionThingsMapper;
    private final CollectionThingsRepository collectionThingsRepository;

    /**
     * Для внутреннего использования (точно будет использоваться в ProfileServiceImpl)
     *
     * @return возвращает коллекции, которые принадлежат профилю с переданным id
     */
    List<CollectionThingsDTO> getAllCollectionThingsByProfileId(Long profileId) {
        return collectionThingsRepository
                .findAllByProfileId(profileId)
                .stream()
                .map(collectionThingsMapper)
                .toList();
    }
}
