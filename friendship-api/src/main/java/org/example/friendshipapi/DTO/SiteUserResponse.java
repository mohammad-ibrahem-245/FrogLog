package org.example.friendshipapi.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteUserResponse {
    private String username;
    private String name;
    private String career;
    private String image;
    
}
