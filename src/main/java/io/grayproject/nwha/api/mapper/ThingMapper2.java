package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.domain.Thing;
import io.grayproject.nwha.api.dto.ThingDTO2;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.function.Function;

/**
 * @author Ilya Avkhimenya
 */
@Component
public class ThingMapper2 implements Function<Thing, ThingDTO2> {

    @Override
    public ThingDTO2 apply(Thing thing) {
        String pattern = "MMMM dd, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        String addDate = simpleDateFormat.format(Date.from(thing.getCreatedAt()));

        return ThingDTO2
                .builder()
                .id(thing.getId())
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
