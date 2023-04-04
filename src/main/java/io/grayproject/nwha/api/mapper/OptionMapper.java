package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.domain.Option;
import io.grayproject.nwha.api.dto.OptionDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author Ilya Avkhimenya
 */
@Component
public class OptionMapper implements Function<Option, OptionDTO> {

    @Override
    public OptionDTO apply(Option option) {
        return OptionDTO
                .builder()
                .id(option.getId())
                .description(option.getDescription())
                .build();
    }
}
