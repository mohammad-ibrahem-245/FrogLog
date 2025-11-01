package org.example.taskapi.feign;


import org.example.taskapi.Dto.ProjectOwner;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "project-api")
public interface ProjectClient {

    @GetMapping("/project/{name}")
    ProjectOwner getProject(@PathVariable String name);

}
