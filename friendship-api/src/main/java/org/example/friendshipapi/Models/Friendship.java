package org.example.friendshipapi.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {

    public Friendship(String siteUser, String friend) {
        this.siteUser = siteUser;
        this.friend = friend;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String siteUser;
    @Column(nullable = false)
    private String friend;
    private String nickname;

}
