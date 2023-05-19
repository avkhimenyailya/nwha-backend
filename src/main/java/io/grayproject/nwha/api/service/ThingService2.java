package io.grayproject.nwha.api.service;

import io.grayproject.nwha.api.dto.ThingDTO2;

import java.security.Principal;
import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
public interface ThingService2 {

    ThingDTO2 getThingById(Long id);

    ThingDTO2 createThing(Principal principal, ThingDTO2 thingDTO2);

    ThingDTO2 updateThing(Principal principal, ThingDTO2 thingDTO2);

    List<ThingDTO2> getArchivedThings(Principal principal);

    List<ThingDTO2> getThingsByTaskOrdinalNumber(Long taskOrdinalNumber);
}
