package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.dto.TaskDTO;
import io.grayproject.nwha.api.mapper.TaskMapper;
import io.grayproject.nwha.api.repository.TaskRepository;
import io.grayproject.nwha.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Override
    public TaskDTO getTaskById(Long id) {
        return taskRepository
                .findById(id)
                .map(taskMapper)
                .orElseThrow(() -> new RuntimeException("Task id: " + id + " not found."));
    }

    @Override
    public TaskDTO getTaskByOrdinalNumber(Integer ordinalNumber) {
        return taskRepository
                .findTaskByOrdinalNumber(ordinalNumber)
                .map(taskMapper)
                .orElseThrow(() -> new RuntimeException("Task #" + ordinalNumber + " not found."));
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository
                .findAll()
                .stream()
                .filter(t -> !t.getHide())
                .map(taskMapper)
                .toList();
    }
}
