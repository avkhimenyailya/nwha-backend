package io.grayproject.nwha.api;

import io.grayproject.nwha.api.util.InitAdmin;
import io.grayproject.nwha.api.util.InitPrimitives;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {
    private final InitPrimitives initPrimitives;
    private final InitAdmin initAdmin;

    // todo before launch!
    private final boolean INIT_PRIMITIVES = true;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return (args) -> {
            if (INIT_PRIMITIVES) {
                initPrimitives.initRoles();
                initPrimitives.initTraits();
                initPrimitives.initTasks();
                initAdmin.init();
            }
        };
    }
}