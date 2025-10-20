package org.example.gateway.FeignClients;

import org.example.gateway.DTO.SiteUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/search/{username}")
    SiteUser search(@PathVariable("username") String username);

    @GetMapping("/random")
    List<SiteUser> random();

    @PostMapping("/add")
    void save(@RequestBody SiteUser user);

    @PutMapping("/update")
    void update(@RequestBody SiteUser user);

    @DeleteMapping("/{username}")
    void delete(@PathVariable("username") String username);
}
