package org.example.userapi.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true , nullable = false)
    private String userid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Column(length = 1000)
    private String bio ;
    @Column(nullable = true)
    private String image ;
    @Column(nullable = false)
    private Date created ;
    private String career;
    @Column(nullable = false)
    private Date dateOfBirth;

}
