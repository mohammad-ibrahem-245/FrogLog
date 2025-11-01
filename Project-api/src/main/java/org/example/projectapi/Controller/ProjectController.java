package org.example.projectapi.Controller;


import org.example.projectapi.DTO.ProjectLeave;
import org.example.projectapi.Model.Project;
import org.example.projectapi.Services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity createProject(@RequestBody Project project){
        if(projectService.createProject(project)){
        return ResponseEntity.ok().build();}
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity deleteProject(@PathVariable String name ,@RequestHeader("X-User-Name") String username){
        if(projectService.deleteProject(name,username)){
        return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/edit/{name}")
    public ResponseEntity editProject(@RequestBody Project project , @PathVariable String name , @RequestHeader("X-User-Name") String username){
        if(projectService.editProject(project,name,username)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/project/{name}")
    public ResponseEntity<Project> getProject(@PathVariable String name){
        if(projectService.getProjectByName(name)!=null){
            return ResponseEntity.ok().body(projectService.getProjectByName(name));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/addmember")
    public ResponseEntity addMember(@RequestBody ProjectLeave projectLeave , @RequestHeader("X-User-Name") String username){
        if(projectService.addMemberToProject(projectLeave,username)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/delmember")
    public ResponseEntity delMember(@RequestBody ProjectLeave projectLeave , @RequestHeader("X-User-Name") String username){
        if(projectService.deleteMemberFromProject(projectLeave,username)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/userprojects/{name}")
    public ResponseEntity<List<Project>> getProjectByOwnerOrMember(@PathVariable String name ){
        projectService.getProjectsByOwnerOrMember(name);
        return ResponseEntity.ok().body(projectService.getProjectsByOwnerOrMember(name));
    }












}
