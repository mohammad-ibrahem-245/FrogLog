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




    /// creating a task
    public void createTask(Task task) {
        task.setStatus(TaskStatus.TODO);
        task.setCreatedDate(LocalDateTime.now());
        task.setEdited(false);
        taskRepository.save(task);
    }


    /// editing existent task
    public boolean editTask(Task task) {
        if (checkExists(task)) {
            task.setEdited(true);
            taskRepository.save(task);
            return true;
        }
        return false;
    }


    /// deleting Existent task
    public boolean deleteTask(Task task){
        if (checkExists(task)) {
            taskRepository.delete(task);
            if (checkExists(task)){
            return true;}

        }
        return false;

    }



    /// searching for task depending on project or user
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




    /// checks if task exist
    public boolean checkExists(Task task){
        return taskRepository.existsById(task.getId());
    }




}
