package io.grayproject.nwha.api.service;

import io.grayproject.nwha.api.dto.AgreementDTO;

import java.security.Principal;

/**
 * @author Ilya Avkhimenya
 */
public interface AgreementService {

    AgreementDTO getAgreement(Principal principal);

    AgreementDTO updateAgreement(Principal principal, Boolean acceptedPhotoUsage);
}
