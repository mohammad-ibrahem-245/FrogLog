package org.example.userapi.Controllers;

import org.example.userapi.Model.SiteUser;
import org.example.userapi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    UserService userService;



    @GetMapping("/Search")
    public ResponseEntity<SiteUser> search(@RequestBody(required = true) String username){
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

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody SiteUser user) throws InterruptedException {
        userService.saveUser(user);

        Optional<SiteUser> confirmationTest = userService.findUser(user.getUserid());
        if(confirmationTest.isPresent()){
            return ResponseEntity.ok().build();
        }else{
        return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/update")
    public ResponseEntity update(@RequestBody SiteUser user){
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody String user ){
        userService.deleteUser(userService.findUser(user).get());

        Optional<SiteUser> confirmationTest = userService.findUser(user);
        if(confirmationTest.isPresent()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok().build();
        }


    }






}
