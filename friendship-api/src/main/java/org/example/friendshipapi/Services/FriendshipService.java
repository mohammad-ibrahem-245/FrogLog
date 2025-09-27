package org.example.friendshipapi.Services;

import org.example.friendshipapi.Models.DeleteRequest;
import org.example.friendshipapi.Models.Friendship;
import org.example.friendshipapi.Models.Request;
import org.example.friendshipapi.Repositories.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    public void addFriendship(String sender, String receiver){
        friendshipRepository.save(new Friendship(sender, receiver));
        friendshipRepository.save(new Friendship(receiver, sender));


    }

    public void deleteFriendship(DeleteRequest deleteRequest){
        friendshipRepository.deleteFriendship(deleteRequest.getSiteUser(), deleteRequest.getFriend());
        friendshipRepository.deleteFriendship(deleteRequest.getFriend(), deleteRequest.getSiteUser());

    }

    public List<String> getFriends(String siteUser){
        Optional<List<String> >friendList = Optional.ofNullable(friendshipRepository.findFriendbySiteuser(siteUser));

        return friendList.orElseGet(ArrayList::new);


    }

    public String searchforAFriend(Request request){
        Optional<String> friend = Optional.ofNullable(friendshipRepository.findFriend(request.getSender() , request.getReceiver()));
        if(friend.isPresent()){
            return friend.get();
        }else{
            return null;
        }
    }
}
