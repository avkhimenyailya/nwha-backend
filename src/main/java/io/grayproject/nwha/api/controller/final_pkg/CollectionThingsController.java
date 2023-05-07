package io.grayproject.nwha.api.controller.final_pkg;

import io.grayproject.nwha.api.dto.CollectionThingsDTO;
import io.grayproject.nwha.api.service.CollectionThingsService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author Ilya Avkhimenya
 */
@RestController
@RequestMapping("/collectionThings")
public class CollectionThingsController {
    private final CollectionThingsService collectionThingsService;

    @Autowired
    public CollectionThingsController(CollectionThingsService collectionThingsService) {
        this.collectionThingsService = collectionThingsService;
    }

    @GetMapping("/count/{thingId}")
    public Integer countCollectionsByThingId(@PathVariable Long thingId) {
        return collectionThingsService.countCollectionsByThingId(thingId);
    }

    @GetMapping("/{id}")
    public CollectionThingsDTO getCollectionThingsById(@PathVariable Long id) {
        return collectionThingsService.getCollectionThingsById(id);
    }

    @PostMapping
    public CollectionThingsDTO createCollectionThings(Principal principal,
                                                      @RequestParam
                                                      @Validated
                                                      @Min(6)
                                                      @Max(20)
                                                      String name) {
        return collectionThingsService.createCollectionThings(principal, name);
    }

    @DeleteMapping("/{id}")
    public void deleteCollectionThing(Principal principal, @PathVariable Long id) {
        collectionThingsService.deleteCollectionThings(principal, id);
    }

    @PutMapping("/{id}")
    public CollectionThingsDTO updateCollectionThingName(Principal principal,
                                                         @PathVariable Long id,
                                                         @RequestParam
                                                         @Validated
                                                         @Min(6)
                                                         @Max(20)
                                                         String name) {
        return collectionThingsService.updateCollectionThingsName(principal, id, name);
    }

    @PutMapping("/{collectionId}/put/{thingId}")
    public CollectionThingsDTO putThingToCollectionThing(Principal principal,
                                                         @PathVariable Long collectionId,
                                                         @PathVariable Long thingId) {
        return collectionThingsService.putThingToCollectionThings(principal, collectionId, thingId);
    }

    @PutMapping("/{collectionId}/rm/{thingId}")
    public CollectionThingsDTO removeThingFromCollectionThing(Principal principal,
                                                              @PathVariable Long collectionId,
                                                              @PathVariable Long thingId) {
        return collectionThingsService.removeThingFromCollectionThings(principal, collectionId, thingId);
    }
}
