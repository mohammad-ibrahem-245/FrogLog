package org.example.taskapi.Controller;

import org.example.taskapi.Dto.SearchBy;
import org.example.taskapi.Models.Task;
import org.example.taskapi.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity createTask(@RequestBody Task task ,  @RequestHeader("X-User-Name") String username) {
        taskService.createTask(task , username);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    public ResponseEntity editTask(@RequestBody Task task , @RequestHeader("X-User-Name") String username) {
       if( taskService.editTask(task , username)) {
           return ResponseEntity.ok().build();
       }
       return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{task}")
    public ResponseEntity deleteTask(@PathVariable Long task , @RequestHeader("X-User-Name") String username) {
        if(taskService.deleteTask(task , username)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();

    }

    @GetMapping("/gettasks")
    public ResponseEntity<List<Task>> getTasks(@RequestBody SearchBy searchBy ,  @RequestHeader("X-User-Name") String username) {
       return ResponseEntity.ok(taskService.searchTasks(searchBy , username));
    }


}
