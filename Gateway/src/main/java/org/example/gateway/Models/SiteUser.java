package org.example.gateway.Models;

import lombok.Data;

import java.util.Date;

@Data
public class SiteUser {
    private Long id;
    private String userid;
    private String name;
    private String password;
    private String bio;
    private String image;
    private String career;
    private Date dateOfBirth;
}
