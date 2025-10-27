package org.example.gateway.Feign;

import org.example.gateway.Models.SiteUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-api")
public interface UserClient {
    @GetMapping("/User/Search/{username}")
    SiteUser search(@PathVariable("username") String username);
}
