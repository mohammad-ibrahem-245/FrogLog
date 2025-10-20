package org.example.friendshipapi.Controller;


import org.example.friendshipapi.FeinClients.UserClient;
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
@RequestMapping("/friendship")
public class FriendShipController {

    @Autowired
    FriendshipRequestService friendshipRequestService;
    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    UserClient userClient;


    @PostMapping("/Add")
    public ResponseEntity addFriend(@RequestBody Request request){
        friendshipRequestService.addFriendship(request);

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/FriendshipsRequests/{username}")
    public ResponseEntity<List<FriendshipRequest>> FriendshipsRequest(@PathVariable String username){
        Optional<List<FriendshipRequest>> requestList=  friendshipRequestService.friendshipRequestList(username);
        if(requestList.isPresent()){
            return ResponseEntity.ok(requestList.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/Answer")
    public ResponseEntity FriendsshipAnswer(@RequestBody RequestAnswer requestAnswer){
        friendshipRequestService.FriendshipAnswer(requestAnswer);
        return ResponseEntity.ok("ok");

    }




    @GetMapping("/allFriends/{username}")
    public ResponseEntity<List<String>> seacrhFriend(@PathVariable String username){
        return ResponseEntity.ok(friendshipService.getFriends(username));



    }

    @DeleteMapping("/deleteFriend")
    public ResponseEntity deleteFriend(@RequestBody DeleteRequest deleteRequest){
        friendshipService.deleteFriendship(deleteRequest);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/search")
    public ResponseEntity<String> searchFriends(@RequestBody Request request){
        return ResponseEntity.ok(friendshipService.searchforAFriend(request));


    }



}
