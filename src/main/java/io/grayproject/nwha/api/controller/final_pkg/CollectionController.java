package io.grayproject.nwha.api.controller.final_pkg;

import io.grayproject.nwha.api.dto.CollectionThingsDTO;
import io.grayproject.nwha.api.service.CollectionThingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author Ilya Avkhimenya
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {
    private final CollectionThingsService collectionThingsService;

    @Autowired
    public CollectionController(CollectionThingsService collectionThingsService) {
        this.collectionThingsService = collectionThingsService;
    }

    @GetMapping("/count/{thingId}")
    public Integer countCollectionsByThingId(@PathVariable Long thingId) {
        return collectionThingsService.countCollectionsByThingId(thingId);
    }

    @GetMapping("/{id}")
    public CollectionThingsDTO getCollectionThingById(@PathVariable Long id) {
        return collectionThingsService.getCollectionThingById(id);
    }

    @PostMapping
    public CollectionThingsDTO createCollectionThing(Principal principal,
                                                     @RequestParam String name) {
        return collectionThingsService.createCollectionThing(principal, name);
    }

    @DeleteMapping("/{id}")
    public void deleteCollectionThing(Principal principal, @PathVariable Long id) {
        collectionThingsService.deleteCollectionThing(principal, id);
    }

    @PutMapping("/{id}")
    public CollectionThingsDTO updateCollectionThingName(Principal principal,
                                                         @PathVariable Long id,
                                                         @RequestParam String name) {
        return collectionThingsService.updateCollectionThingName(principal, id, name);
    }

    @PutMapping("/{collectionId}/put/{thingId}")
    public CollectionThingsDTO putThingToCollectionThing(Principal principal,
                                                         @PathVariable Long collectionId,
                                                         @PathVariable Long thingId) {
        return collectionThingsService.putThingToCollectionThing(principal, collectionId, thingId);
    }

    @PutMapping("/{collectionId}/rm/{thingId}")
    public CollectionThingsDTO removeThingFromCollectionThing(Principal principal,
                                                              @PathVariable Long collectionId,
                                                              @PathVariable Long thingId) {
        return collectionThingsService.removeThingFromCollectionThing(principal, collectionId, thingId);
    }
}
