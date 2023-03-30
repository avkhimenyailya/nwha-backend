package io.grayproject.nwha.api.exception;

/**
 * @author Ilya Avkhimenya
 */
public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException(Long profileId) {
        super(String.format("Profile (id: %s) not found", profileId));
    }
}
