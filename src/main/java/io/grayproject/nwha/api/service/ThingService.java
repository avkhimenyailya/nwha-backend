package io.grayproject.nwha.api.service;

import io.grayproject.nwha.api.dto.RecentlyThingDTO;
import io.grayproject.nwha.api.dto.ThingDTO;

import java.security.Principal;
import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
public interface ThingService {

    ThingDTO getThingById(Long id);

    List<ThingDTO> getArchivedThings(Principal principal);

    List<RecentlyThingDTO> getRecentlyThings();

    List<ThingDTO> getThingsByTaskId(Long taskId);

    ThingDTO createThing(Principal principal, ThingDTO thingDTO);

    ThingDTO updateThing(Principal principal, ThingDTO thingDTO);
}
