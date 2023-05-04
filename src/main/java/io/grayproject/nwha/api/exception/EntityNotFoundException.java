package io.grayproject.nwha.api.exception;

/**
 * @author Ilya Avkhimenya
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Long id) {
        super(String.format("Entity (id: %s) not found", id));
    }

    public EntityNotFoundException(String string) {
        super(String.format("Entity (string: %s) not found", string));
    }

    public EntityNotFoundException(Class<?> cl, Object obj) {
        super("Entity: " + cl.getName() + ", index: " + obj + " — not found");
    }
}
