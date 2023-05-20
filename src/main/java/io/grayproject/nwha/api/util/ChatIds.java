package io.grayproject.nwha.api.util;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Ilya Avkhimenya
 */
@Component
public class ChatIds {

    public final Set<Long> chatIds = new CopyOnWriteArraySet<>();
}
