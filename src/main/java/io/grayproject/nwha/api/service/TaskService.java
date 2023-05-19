package io.grayproject.nwha.api.service;

import io.grayproject.nwha.api.dto.TaskDTO;

import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
public interface TaskService {

    TaskDTO getTaskById(Long id);

    TaskDTO getTaskByOrdinalNumber(Integer ordinalNumber);

    List<TaskDTO> getAllTasks();
}
