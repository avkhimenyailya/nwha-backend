package io.grayproject.nwha.api.dto.authentication;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ChangePasswordDTO {

    @Length(min = 8, max = 32)
    private String oldPassword;
    @Length(min = 8, max = 32)
    private String newPassword;
}
