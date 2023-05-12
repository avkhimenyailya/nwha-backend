package io.grayproject.nwha.api.controller.final_pkg;

import io.grayproject.nwha.api.dto.AgreementDTO;
import io.grayproject.nwha.api.service.AgreementService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author Ilya Avkhimenya
 */
@RestController
@RequestMapping("/agreement")
@RequiredArgsConstructor
public class AgreementController {
    private final AgreementService agreementService;

    @GetMapping
    public ResponseEntity<AgreementDTO> getAgreement(Principal principal) {
        return ResponseEntity.ok(agreementService.getAgreement(principal));
    }

    @PutMapping
    public ResponseEntity<AgreementDTO> updateAgreement(Principal principal,
                                                        @RequestParam
                                                        @Validated
                                                        @NotNull
                                                        Boolean acceptedPhotoUsage) {
        return ResponseEntity.ok(agreementService.updateAgreement(principal, acceptedPhotoUsage));
    }
}
