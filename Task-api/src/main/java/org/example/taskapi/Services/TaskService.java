package org.example.taskapi.Services;


import org.example.taskapi.Dto.SearchBy;
import org.example.taskapi.Enums.TaskStatus;
import org.example.taskapi.Models.Task;
import org.example.taskapi.Repositories.TaskRepository;
import org.example.taskapi.feign.ProjectClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectClient projectClient;




    /// creating a task
    public void createTask(Task task , String username) {
        String owner = projectClient.getProject(task.getProjectName()).getOwner();
        if (owner.equals(username)) {
            task.setOwner(owner);
            task.setStatus(TaskStatus.TODO);
            task.setCreatedDate(LocalDateTime.now());
            task.setEdited(false);
            taskRepository.save(task);
        }
    }


    /// editing existent task
    public boolean editTask(Task task , String username) {
        String owner = projectClient.getProject(task.getProjectName()).getOwner();
        if (owner.equals(username)) {
            if (checkExists(task)) {
                task.setEdited(true);
                taskRepository.save(task);
                return true;
            }
        }
        return false;
    }


    /// deleting Existent task
    public boolean deleteTask(Long id , String username) {
        Task task = taskRepository.findById(id).get();
        String owner = projectClient.getProject(task.getProjectName()).getOwner();
        if (owner.equals(username)) {
            if (taskRepository.existsById(task.getId())) {
                taskRepository.delete(task);
                if (!checkExists(task)) {
                    return true;
                }

            }
        }
        return false;

    }



    /// searching for task depending on project or user
    public List<Task> searchTasks(SearchBy searchBy , String username) {

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



    /// query gets fired every 60 seconds to set task as overdue
    @Scheduled(fixedRate = 60000)
    public void markOverdueTasks() {
        List<Task> allTasks = taskRepository.findAll();  // O(n)

        LocalDateTime now = LocalDateTime.now();
        int updatedCount = 0;

        for (Task task : allTasks) {                     // O(n)
            if (task.getDueDate() != null
                    && task.getDueDate().isBefore(now)
                    && task.getStatus() != TaskStatus.OVERDUE) {

                task.setStatus(TaskStatus.OVERDUE);
                taskRepository.save(task);                // O(1) per update
                updatedCount++;
            }
        }
    }



    /// checks if task exist
    public boolean checkExists(Task task){
        return taskRepository.existsById(task.getId());
    }




}
