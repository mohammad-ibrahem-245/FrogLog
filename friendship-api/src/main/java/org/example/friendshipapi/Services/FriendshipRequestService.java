package org.example.friendshipapi.Services;


import org.example.friendshipapi.Enums.RequestStatus;
import org.example.friendshipapi.Models.FriendshipRequest;
import org.example.friendshipapi.Models.Request;
import org.example.friendshipapi.Models.RequestAnswer;
import org.example.friendshipapi.Repositories.FriendshipRepository;
import org.example.friendshipapi.Repositories.FriendshipRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FriendshipRequestService {

    @Autowired
    FriendshipRequestRepository friendshipRequestRepository;
    @Autowired
    FriendshipService friendshipService;



    public void addFriendship(Request request){
        FriendshipRequest friendshipRequest = new FriendshipRequest();
        friendshipRequest.setDate(LocalDate.now());
        friendshipRequest.setStatus(RequestStatus.PENDING.toString());
        friendshipRequest.setSender(request.getSender());
        friendshipRequest.setReceiver(request.getReceiver());
        friendshipRequestRepository.save(friendshipRequest);
    }

    public Optional<List<FriendshipRequest>> friendshipRequestList(String username){

        return Optional.ofNullable(friendshipRequestRepository.findByReceiverAndStatus(username , RequestStatus.PENDING.toString()));
    }

    public void FriendshipAnswer(RequestAnswer requestAnswer){
        Optional<FriendshipRequest> friendshipRequest = Optional.ofNullable(friendshipRequestRepository.findBySenderAndReceiverAndStatus(requestAnswer.getSender(), requestAnswer.getReceiver(), RequestStatus.PENDING.toString()));
        if(friendshipRequest.isPresent()){
        if (requestAnswer.getStatus().equals(RequestStatus.ACCEPTED.toString())){
            friendshipService.addFriendship(requestAnswer.getSender(), requestAnswer.getReceiver());
            friendshipRequest.get().setStatus(RequestStatus.ACCEPTED.toString());
            friendshipRequestRepository.save(friendshipRequest.get());
        } else if (requestAnswer.getStatus().equals(RequestStatus.REJECTED.toString())) {
            friendshipRequest.get().setStatus(RequestStatus.REJECTED.toString());
            friendshipRequestRepository.save(friendshipRequest.get());
        }}


    }


}
