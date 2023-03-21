package io.grayproject.nwha.api.service;

import io.grayproject.nwha.api.dto.register.RegisterRequestDTO;
import io.grayproject.nwha.api.exception.BadInvitationCodeException;

/**
 * @author Ilya Avkhimenya
 */
public interface RegisterService {

    String register(RegisterRequestDTO registerRequestDTO)
            throws BadInvitationCodeException;
}
