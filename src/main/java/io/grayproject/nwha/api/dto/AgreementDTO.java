package io.grayproject.nwha.api.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ilya Avkhimenya
 */
@Data
@Builder
public class AgreementDTO {

    private Long profileId;
    private Boolean acceptedPhotoUsage;
}
