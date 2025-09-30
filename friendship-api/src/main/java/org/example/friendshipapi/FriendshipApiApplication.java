package org.example.friendshipapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FriendshipApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendshipApiApplication.class, args);
    }

}
