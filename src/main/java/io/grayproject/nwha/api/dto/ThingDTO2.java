package io.grayproject.nwha.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * @author Ilya Avkhimenya
 */
@Data
@Builder
public class ThingDTO2 {

    private Long id;
    private String description;
    private boolean archived;
    private boolean removed;
    private String addDate;

    @NotNull
    private Long profileTaskId;

    @NotNull
    private String pictureLink;
}