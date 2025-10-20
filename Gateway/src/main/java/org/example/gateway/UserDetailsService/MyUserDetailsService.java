package org.example.gateway.UserDetailsService;


import org.example.gateway.DTO.SiteUser;
import org.example.gateway.FeignClients.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService{


        @Autowired
        private UserClient userClient;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            SiteUser user = userClient.search(username);

            return new org.springframework.security.core.userdetails.User(user.getUserid(), user.getPassword(), new ArrayList<>());
        }



}

