package io.grayproject.nwha.api.exception;

/**
 * @author Ilya Avkhimenya
 */
public class BadInvitationCodeException extends RuntimeException {

    public BadInvitationCodeException(String invCode) {
        super(String.format("The invitation code [%s] does not exist", invCode));
    }
}
