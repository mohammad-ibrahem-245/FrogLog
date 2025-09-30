package org.example.taskapi.Controller;

import org.example.taskapi.Dto.SearchBy;
import org.example.taskapi.Models.Task;
import org.example.taskapi.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity createTask(@RequestBody Task task) {
        taskService.createTask(task);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    public ResponseEntity editTask(@RequestBody Task task) {
        taskService.editTask(task);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteTask(@RequestBody Task rask){
        taskService.deleteTask(rask);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/getTasks")
    public ResponseEntity<List<Task>> getTasks(@RequestBody SearchBy searchBy){
       return ResponseEntity.ok(taskService.searchTasks(searchBy));
    }


}
