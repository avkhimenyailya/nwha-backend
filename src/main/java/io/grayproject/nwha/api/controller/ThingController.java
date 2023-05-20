package io.grayproject.nwha.api.controller;

import io.grayproject.nwha.api.dto.RecentlyThingDTO;
import io.grayproject.nwha.api.dto.ThingDTO;
import io.grayproject.nwha.api.service.ThingService;
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
@RequestMapping("/thing")
@RequiredArgsConstructor
public class ThingController {
    private final ThingService thingService;

    @GetMapping("/{id}")
    public ResponseEntity<ThingDTO> getThingById(@PathVariable Long id) {
        return ResponseEntity.ok(thingService.getThingById(id));
    }

    @GetMapping("/archived")
    public ResponseEntity<List<ThingDTO>> getArchivedThings(Principal principal) {
        return ResponseEntity.ok(thingService.getArchivedThings(principal));
    }

    @GetMapping("/recently")
    public ResponseEntity<List<RecentlyThingDTO>> getRecentlyThings() {
        return ResponseEntity.ok(thingService.getRecentlyThings());
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<ThingDTO>> getThingsByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok(thingService.getThingsByTaskId(taskId));
    }

    @PostMapping
    public ResponseEntity<ThingDTO> createThing(Principal principal,
                                                @RequestBody
                                                @Validated
                                                ThingDTO thingDTO) {
        return ResponseEntity.ok(thingService.createThing(principal, thingDTO));
    }

    @PutMapping
    public ResponseEntity<ThingDTO> updateThing(
            Principal principal, @RequestBody @Validated ThingDTO thingDTO) {
        return ResponseEntity.ok(thingService.updateThing(principal, thingDTO));
    }
}
