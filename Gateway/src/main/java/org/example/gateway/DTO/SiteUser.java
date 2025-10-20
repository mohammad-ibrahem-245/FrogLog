package org.example.gateway.DTO;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteUser {

    private Long id;
    private String userid;
    private String name;
    private String password;
    private String bio ;
    private String image ;
    private Date created ;
    private String career;
    private Date dateOfBirth;


}
