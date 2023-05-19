package io.grayproject.nwha.api.controller;

import io.grayproject.nwha.api.dto.ThingDTO2;
import io.grayproject.nwha.api.service.ThingService2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@RestController
@RequestMapping("/thing2")
@RequiredArgsConstructor
public class ThingController2 {
    private final ThingService2 thingService2;

    @GetMapping("/{id}")
    public ResponseEntity<ThingDTO2> getThingById(@PathVariable Long id) {
        return ResponseEntity.ok(thingService2.getThingById(id));
    }

    @PostMapping
    public ResponseEntity<ThingDTO2> createThing(
            Principal principal, @RequestBody @Validated ThingDTO2 thingDTO2) {
        return ResponseEntity.ok(thingService2.createThing(principal, thingDTO2));
    }

    @PutMapping
    public ResponseEntity<ThingDTO2> updateThing(
            Principal principal, @RequestBody @Validated ThingDTO2 thingDTO2) {
        return ResponseEntity.ok(thingService2.updateThing(principal, thingDTO2));
    }

    @GetMapping("/archived")
    public ResponseEntity<List<ThingDTO2>> getArchivedThings(Principal principal) {
        return ResponseEntity.ok(thingService2.getArchivedThings(principal));
    }

    @GetMapping("/taskNumber/{taskOrdinalNumber}")
    public ResponseEntity<List<ThingDTO2>> getThingsByTaskOrdinalNumber(@PathVariable Long taskOrdinalNumber) {
        return ResponseEntity.ok(thingService2.getThingsByTaskOrdinalNumber(taskOrdinalNumber));
    }
}
