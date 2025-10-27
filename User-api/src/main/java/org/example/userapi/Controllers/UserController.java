package org.example.userapi.Controllers;

import org.example.userapi.Model.SiteUser;
import org.example.userapi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserService userService;



    @GetMapping("/search/{username}")
    public ResponseEntity<SiteUser> search(@PathVariable String username){
       Optional<SiteUser> user = userService.findUser(username);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/random")
    public ResponseEntity<List<SiteUser>> random(){
        Optional<List<SiteUser>> randomUsers = userService.findRandomUser();
        if(randomUsers.isPresent()){
            return ResponseEntity.ok(randomUsers.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/signup")
    public ResponseEntity save(@RequestBody SiteUser user) {

        if (userService.saveUser(user)) {
            return ResponseEntity.ok().build();
        }else  {
            return ResponseEntity.badRequest().build();
        }

    }


    @PostMapping("/update/")
    public ResponseEntity update(@RequestBody SiteUser user){
        if (userService.updateUser(user)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/deleteMyAccount/{username}")
    public ResponseEntity delete(@PathVariable String username ){
        if (userService.deleteUser(username)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();


    }






}
