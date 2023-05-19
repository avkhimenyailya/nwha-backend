package io.grayproject.nwha.api.controller;

import io.grayproject.nwha.api.dto.TaskDTO;
import io.grayproject.nwha.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/number/{ordinalNumber}")
    public TaskDTO getTaskByOrdinalNumber(@PathVariable Integer ordinalNumber) {
        return taskService.getTaskByOrdinalNumber(ordinalNumber);
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }
}
