package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.domain.Thing;
import io.grayproject.nwha.api.dto.ThingDTO;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.function.Function;

/**
 * @author Ilya Avkhimenya
 */
@Component
public class ThingMapper implements Function<Thing, ThingDTO> {

    @Override
    public ThingDTO apply(Thing thing) {
        String pattern = "MMMM dd, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        String addDate = simpleDateFormat.format(Date.from(thing.getCreatedAt()));

        return ThingDTO
                .builder()
                .id(thing.getId())
                .profileId(thing.getProfileTask().getProfile().getId())
                .taskId(thing.getProfileTask().getTask().getId())
                .profileTaskId(thing.getProfileTask().getId())
                .pictureLink(thing.getFileUrl())
                .description(thing.getDescription())
                .archived(thing.isArchived())
                .removed(thing.isRemoved())
                .addDate(addDate)
                .build();
    }
}
