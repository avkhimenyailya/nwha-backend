package io.grayproject.nwha.api.service;

import io.grayproject.nwha.api.dto.CollectionThingsDTO;

import java.security.Principal;
import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
public interface CollectionThingsService {

    Integer countCollectionsByThingId(Long thingId);

    CollectionThingsDTO getCollectionThingsById(Long id);

    CollectionThingsDTO createCollectionThings(Principal principal, String name);

    void deleteCollectionThings(Principal principal, Long id);

    CollectionThingsDTO updateCollectionThingsName(Principal principal, Long id, String name);

    CollectionThingsDTO putThingToCollectionThings(Principal principal, Long collectionId, Long thingId);

    CollectionThingsDTO removeThingFromCollectionThings(Principal principal, Long collectionId, Long thingId);

    List<CollectionThingsDTO> getCollectionThingsByProfileId(Long profileId);

    List<CollectionThingsDTO> getCollectionThingsPrincipal(Principal principal);
}
