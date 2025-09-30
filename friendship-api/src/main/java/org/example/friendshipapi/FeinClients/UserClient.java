package org.example.friendshipapi.FeinClients;

import org.example.friendshipapi.DTO.SiteUserResponse;
import org.hibernate.annotations.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-API")

public interface UserClient {
    @GetMapping("/User/search")
    SiteUserResponse getUser(@RequestBody String username);
}
