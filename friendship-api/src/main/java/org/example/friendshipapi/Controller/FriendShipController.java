package org.example.friendshipapi.Controller;



import org.example.friendshipapi.Models.DeleteRequest;
import org.example.friendshipapi.Models.FriendshipRequest;
import org.example.friendshipapi.Models.Request;
import org.example.friendshipapi.Models.RequestAnswer;
import org.example.friendshipapi.Services.FriendshipRequestService;
import org.example.friendshipapi.Services.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
public class FriendShipController {

    @Autowired
    FriendshipRequestService friendshipRequestService;
    @Autowired
    private FriendshipService friendshipService;


    @PostMapping("/add")
    public ResponseEntity addFriend(@RequestBody Request request ,  @RequestHeader("X-User-Name") String username){
        friendshipRequestService.addFriendship(request, username);

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/friendshipsrequests/{username}")
    public ResponseEntity<List<FriendshipRequest>> FriendshipsRequest(@PathVariable String username ,  @RequestHeader("X-User-Name") String user){
        Optional<List<FriendshipRequest>> requestList=  friendshipRequestService.friendshipRequestList(username , user);
        if(requestList.isPresent()){
            return ResponseEntity.ok(requestList.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }



    @PutMapping("/answer")
    public ResponseEntity FriendsshipAnswer(@RequestBody RequestAnswer requestAnswer ,  @RequestHeader("X-User-Name") String user ){
        friendshipRequestService.FriendshipAnswer(requestAnswer , user);
        return ResponseEntity.ok("ok");

    }




    @GetMapping("/allfriends/{username}")
    public ResponseEntity<List<String>> seacrhFriend(@PathVariable String username ,  @RequestHeader("X-User-Name") String user){
        return ResponseEntity.ok(friendshipService.getFriends(username , user));



    }

    @DeleteMapping("/deletefriend")
    public ResponseEntity deleteFriend(@RequestBody DeleteRequest deleteRequest , @RequestHeader("X-User-Name") String user){
        friendshipService.deleteFriendship(deleteRequest,user);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/search")
    public ResponseEntity<String> searchFriends(@RequestBody Request request , @RequestHeader("X-User-Name") String user){
        return ResponseEntity.ok(friendshipService.searchforAFriend(request,user));


    }



}
