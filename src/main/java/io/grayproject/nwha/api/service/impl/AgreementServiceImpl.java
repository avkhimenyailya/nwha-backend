package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.domain.Agreement;
import io.grayproject.nwha.api.domain.Profile;
import io.grayproject.nwha.api.dto.AgreementDTO;
import io.grayproject.nwha.api.repository.AgreementRepository;
import io.grayproject.nwha.api.service.AgreementService;
import io.grayproject.nwha.api.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * @author Ilya Avkhimenya
 */
@Service
@RequiredArgsConstructor
public class AgreementServiceImpl implements AgreementService {
    private final ProfileService profileService;
    private final AgreementRepository agreementRepository;

    @Override
    public AgreementDTO getAgreement(Principal principal) {
        Agreement agreementEntity = getAgreementEntity(principal);
        return AgreementDTO
                .builder()
                .profileId(agreementEntity.getProfile().getId())
                .acceptedPhotoUsage(agreementEntity.getAcceptedPhotoUsage())
                .build();
    }

    @Override
    public AgreementDTO updateAgreement(Principal principal, Boolean acceptedPhotoUsage) {
        Agreement agreementEntity = getAgreementEntity(principal);
        agreementEntity.setAcceptedPhotoUsage(acceptedPhotoUsage);
        agreementRepository.save(agreementEntity);
        return AgreementDTO
                .builder()
                .profileId(agreementEntity.getProfile().getId())
                .acceptedPhotoUsage(agreementEntity.getAcceptedPhotoUsage())
                .build();
    }

    private Agreement getAgreementEntity(Principal principal) {
        Profile profile = profileService.getProfileEntityByPrincipal(principal);
        return agreementRepository
                .findAgreementByProfileId(profile.getId())
                .orElseThrow(() ->
                        new RuntimeException("Не найдены соглашения для профиля " + profile.getUser().getUsername()));
    }
}
