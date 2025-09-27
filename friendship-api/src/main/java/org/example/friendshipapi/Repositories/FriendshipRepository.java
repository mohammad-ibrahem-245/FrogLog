package org.example.friendshipapi.Repositories;


import jakarta.transaction.Transactional;
import org.example.friendshipapi.Models.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {


    @Query("select f.friend from Friendship f where f.siteUser = :username ")
    public List<String> findFriendbySiteuser(@Param("username") String siteUser);


    @Modifying
    @Transactional
    @Query("DELETE FROM Friendship f WHERE f.siteUser = :siteUser AND f.friend = :friend")
    void deleteFriendship(@Param("siteUser") String siteUser, @Param("friend") String friend);

    @Query("select f.friend from Friendship f where f.siteUser = :username and f.friend = :friend")
    public String findFriend(@Param("username") String siteUser, @Param("friend") String friend);

}
