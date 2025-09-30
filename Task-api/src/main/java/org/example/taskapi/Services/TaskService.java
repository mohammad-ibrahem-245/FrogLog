package org.example.taskapi.Services;


import org.example.taskapi.Dto.SearchBy;
import org.example.taskapi.Enums.TaskStatus;
import org.example.taskapi.Models.Task;
import org.example.taskapi.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public void createTask(Task task) {
        task.setStatus(TaskStatus.TODO);
        task.setCreatedDate(LocalDateTime.now());
        task.setEdited(false);
        taskRepository.save(task);
    }

    public void editTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Task task){
        taskRepository.delete(task);
    }
    
    public List<Task> searchTasks(SearchBy searchBy){
        if (searchBy.getField() == SearchBy.SearchByField.PROJECT) {
            Optional<List<Task>> tasks = Optional.ofNullable(taskRepository.findByProjectId(searchBy.getValue()));
            if (tasks.isPresent()) {
                return tasks.get();
            }else return null;

        } else if (searchBy.getField() == SearchBy.SearchByField.ASSIGNEE ) {
            Optional<List<Task>> tasks = Optional.ofNullable(taskRepository.findByAssigneeIgnoreCase(searchBy.getValue()));
            if (tasks.isPresent()) {
                return tasks.get();
            }else return null;
        }
        return null;
    }




}
