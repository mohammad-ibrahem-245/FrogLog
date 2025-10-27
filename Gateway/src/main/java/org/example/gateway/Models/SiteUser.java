package org.example.gateway.Models;

import lombok.Data;

@Data
public class SiteUser {
    private Long id;
    private String userid;
    private String username;  // or use `userid` if that’s your login field
    private String password;
    private String name;
    private String bio;
    private String image;
    private String career;
}
