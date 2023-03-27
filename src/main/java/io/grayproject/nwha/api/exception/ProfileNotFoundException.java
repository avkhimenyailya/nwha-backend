package io.grayproject.nwha.api.exception;

/**
 * @author Ilya Avkhimenya
 */
public class ProfileNotFoundException extends Exception {

    public ProfileNotFoundException(Long profileId) {
        super("Profile with id: " + profileId + " not found");
    }
}
