package io.grayproject.nwha.api.exception;

/**
 * @author Ilya Avkhimenya
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Long id) {
        // todo (сделать уточнение)
        super(String.format("Entity (id: %s) not found", id));
    }
}
