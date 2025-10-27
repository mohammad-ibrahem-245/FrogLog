package org.example.userapi.FeignClients;


import org.example.userapi.Dto.ProjectLeave;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PROJECT-API")
public interface ProjectClient {

    @PutMapping("/delMember")
    public ResponseEntity delMember(@RequestBody ProjectLeave membersRequests);
}
