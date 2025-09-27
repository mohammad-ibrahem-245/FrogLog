package org.example.friendshipapi.Repositories;


import org.example.friendshipapi.Models.FriendshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long> {

    public List<FriendshipRequest> findByReceiverAndStatus(String receiver ,String status);

    public FriendshipRequest findBySenderAndReceiverAndStatus(String sender, String receiver, String status);

}
