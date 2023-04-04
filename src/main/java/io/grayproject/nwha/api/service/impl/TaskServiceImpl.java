package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.dto.TaskDTO;
import io.grayproject.nwha.api.mapper.TaskMapper;
import io.grayproject.nwha.api.repository.TaskRepository;
import io.grayproject.nwha.api.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskMapper taskMapper, TaskRepository taskRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository
                .findAll()
                .stream()
                .map(taskMapper)
                .toList();
    }

    @Override
    public TaskDTO getTaskByOrdinalNumber(Integer ordinalNumber) {
        return taskRepository
                .findTaskByOrdinalNumber(ordinalNumber)
                .map(taskMapper)
                .orElseThrow(() -> new RuntimeException("Task #" + ordinalNumber + " not found."));
    }
}
