package io.grayproject.nwha.api.service;

import io.grayproject.nwha.api.dto.CollectionThingsDTO;

import java.security.Principal;

/**
 * @author Ilya Avkhimenya
 */
public interface CollectionThingsService {

    Integer countCollectionsByThingId(Long thingId);

    CollectionThingsDTO getCollectionThingById(Long id);

    CollectionThingsDTO createCollectionThing(Principal principal, String name);

    void deleteCollectionThing(Principal principal, Long id);

    CollectionThingsDTO updateCollectionThingName(Principal principal, Long id, String name);

    CollectionThingsDTO putThingToCollectionThing(Principal principal, Long collectionId, Long thingId);

    CollectionThingsDTO removeThingFromCollectionThing(Principal principal, Long collectionId, Long thingId);
}
