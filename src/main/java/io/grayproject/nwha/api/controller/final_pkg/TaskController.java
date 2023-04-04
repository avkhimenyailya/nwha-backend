package io.grayproject.nwha.api.controller.final_pkg;

import io.grayproject.nwha.api.dto.TaskDTO;
import io.grayproject.nwha.api.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{ordinalNumber}")
    public TaskDTO getTaskById(@PathVariable Integer ordinalNumber) {
        return taskService.getTaskByOrdinalNumber(ordinalNumber);
    }
}
