package io.grayproject.nwha.api.exception;

/**
 * @author Ilya Avkhimenya
 */
public class BadInvitationCodeException extends Exception {

    public BadInvitationCodeException() {
        super("The invitation code does not exist");
    }
}
