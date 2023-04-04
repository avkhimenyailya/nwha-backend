package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.domain.Task;
import io.grayproject.nwha.api.dto.TaskDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author Ilya Avkhimenya
 */
@Component
public class TaskMapper implements Function<Task, TaskDTO> {
    private final QuestionMapper questionMapper;

    public TaskMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Override
    public TaskDTO apply(Task task) {
        return TaskDTO
                .builder()
                .id(task.getId())
                .description(task.getDescription())
                .ordinalNumber(task.getOrdinalNumber())
                .questions(task.getQuestions().stream().map(questionMapper).toList())
                .build();
    }
}
