package io.grayproject.nwha.api.controller.final_pkg;

import io.grayproject.nwha.api.dto.RecentlyAddedThingDTO;
import io.grayproject.nwha.api.dto.ThingDTO;
import io.grayproject.nwha.api.service.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@RestController
@RequestMapping("/thing")
public class ThingController {
    private final ThingService thingService;

    @Autowired
    public ThingController(ThingService thingService) {
        this.thingService = thingService;
    }

    @GetMapping("/random")
    public List<ThingDTO> getRandomThings(@RequestParam Integer limit) {
        return thingService.getRandomThings(limit);
    }

    @GetMapping("/recently")
    public List<RecentlyAddedThingDTO> getRecentlyAddedThings() {
        return thingService.getRecentlyAddedThings();
    }

    @GetMapping("/{id}")
    public ThingDTO getThingById(@PathVariable Long id) {
        return thingService.getThingById(id);
    }

    @PostMapping("/upload")
    public ThingDTO setImageUrl(Principal principal,
                                @RequestParam("file") MultipartFile file,
                                @RequestParam("thingId") String thingId)
            throws IOException {
        return thingService.setImageUrl(principal, file, thingId);
    }

    @PostMapping
    public ThingDTO createThing(Principal principal,
                                @Validated @RequestBody ThingDTO thingDTO) {
        return thingService.createThing(principal, thingDTO);
    }

    @PutMapping
    public ThingDTO updateThing(Principal principal, ThingDTO thingDTO) {
        // todo
        return null;
    }

    @DeleteMapping
    public void deleteThing(Principal principal, Long id) {
        // todo
    }
}
