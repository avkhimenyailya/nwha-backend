package io.grayproject.nwha.api.util;

import io.grayproject.nwha.api.domain.*;
import io.grayproject.nwha.api.repository.RoleRepository;
import io.grayproject.nwha.api.repository.TaskRepository;
import io.grayproject.nwha.api.repository.TraitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InitPrimitives {
    @Value("classpath:primitives/tasks.yml")
    private Resource TASKS_YAML;
    @Value("classpath:primitives/traits.yml")
    private Resource TRAITS_YAML;
    @Value("classpath:primitives/roles.yml")
    private Resource ROLES_YAML;

    private final RoleRepository roleRepository;
    private final TaskRepository taskRepository;
    private final TraitRepository traitRepository;

    private final Locale locale = Locale.ENG;

    @Transactional
    public void initTasks() {
        // Get YAML file from resources.primitives.tasks.yml
        Map<String, Object> yaml = getYaml(TASKS_YAML);
        // Define language
        String keyForDescription =
                locale == Locale.ENG ? "description-english" : "description-russian";

        // Get tasks array from yaml
        @SuppressWarnings("unchecked")
        List<Object> tasks = (List<Object>) yaml.get("tasks");
        List<Task> taskEntities = tasks
                .stream()
                .map(taskObj -> {
                    // Create new task entity
                    @SuppressWarnings("unchecked")
                    Map<String, Object> taskMap = (Map<String, Object>) taskObj;
                    Task newTask = Task.builder()
                            .ordinalNumber((Integer) taskMap.get("ordinal-number"))
                            .description((String) taskMap.get(keyForDescription))
                            .build();

                    // Get questions array from yaml.tasks
                    @SuppressWarnings("unchecked")
                    List<Object> questions = (List<Object>) taskMap.get("questions");
                    List<Question> questionEntities = questions
                            .stream()
                            .map(questionObj -> {
                                // Create new question entity
                                @SuppressWarnings("unchecked")
                                Map<String, Object> questionMap = (Map<String, Object>) questionObj;
                                Question newQuestion = Question.builder()
                                        .task(newTask)
                                        .ordinalNumber((Integer) questionMap.get("ordinal-number"))
                                        .build();

                                // Get option array from yaml.tasks.questions
                                @SuppressWarnings("unchecked")
                                List<Object> options = (List<Object>) questionMap.get("options");
                                List<Option> optionEntities = options
                                        .stream()
                                        .map(optionObj -> {
                                            // Create new option entity
                                            @SuppressWarnings("unchecked")
                                            Map<String, Object> optionMap = (Map<String, Object>) optionObj;
                                            return Option.builder()
                                                    .question(newQuestion)
                                                    .description((String) optionMap.get(keyForDescription))
                                                    .build();
                                        })
                                        .toList();
                                log.info("Options successfully generated, total entities: {}", optionEntities.size());

                                // Set options to question
                                newQuestion.setOptions(optionEntities);
                                return newQuestion;
                            })
                            .sorted(Comparator.comparing(Question::getOrdinalNumber))
                            .toList();
                    log.info("Questions successfully generated, total entities: {}", questionEntities.size());

                    // Set questions to task
                    newTask.setQuestions(questionEntities);
                    return newTask;
                })
                .sorted(Comparator.comparing(Task::getOrdinalNumber))
                .toList();
        log.info("Tasks successfully generated, total entities: {}", taskEntities.size());

        // save db
        taskRepository.saveAll(taskEntities);
    }

    @Transactional
    public void initTraits() {
        // Get YAML file from resources.primitives.tasks.yml
        Map<String, Object> yaml = getYaml(TRAITS_YAML);

        // Get traits array from yaml
        @SuppressWarnings("unchecked")
        List<Object> traits = (List<Object>) yaml.get("traits");
        List<Trait> traitEntities = traits
                .stream()
                .map(traitObj ->
                        Trait.builder().name((String) traitObj).build()
                )
                .toList();
        log.info("Traits successfully generated, total entities: {}", traitEntities.size());

        traitRepository.saveAll(traitEntities);
    }


    @Transactional
    public void initRoles() {
        // Get YAML file from resources.primitives.roles.yml
        Map<String, Object> yaml = getYaml(ROLES_YAML);

        // Get roles array from yaml
        @SuppressWarnings("unchecked")
        List<String> rolesStrings = (List<String>) yaml.get("roles");
        List<Role> rolesEntities = rolesStrings
                .stream()
                .map(rolesString -> {
                    ERole eRole = null;
                    for (ERole roleName : ERole.values()) {
                        if (roleName.name().equals(rolesString)) {
                            eRole = roleName;
                            break;
                        }
                    }
                    return Role
                            .builder()
                            .name(eRole)
                            .build();
                })
                .toList();
        log.info("Roles successfully generated, total entities: {}", rolesEntities.size());

        roleRepository.saveAll(rolesEntities);
    }

    public Map<String, Object> getYaml(Resource resource) {
        try {
            return new Yaml().load(resource.getInputStream());
        } catch (IOException e) {
            log.error("Failed to retrieve file from resources");
            throw new RuntimeException(e);
        }
    }

    enum Locale {
        RU,
        ENG
    }
}