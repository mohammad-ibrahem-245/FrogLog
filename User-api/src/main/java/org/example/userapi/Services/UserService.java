package org.example.userapi.Services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.example.userapi.Model.SiteUser;
import org.example.userapi.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    private final BCrypt.Hasher passwordHasher;

    public UserService(BCrypt.Hasher passwordHasher) {
        this.passwordHasher = passwordHasher;
    }

    public Optional<SiteUser> findUser(String username) {
        Optional<SiteUser> user = Optional.ofNullable(userRepo.findByUserid(username));
        return user;
    }

    public Optional<List<SiteUser>> findRandomUser(){
        Optional<List<SiteUser>> user = userRepo.findRandomUser();
        return user;
    }

    public void saveUser(SiteUser user){
        user.setPassword(passwordHasher.hashToString(12, user.getPassword().toCharArray()));
        user.setCreated(Date.from(new Date().toInstant()));
        userRepo.save(user);
    }

    public void deleteUser(SiteUser user){
        userRepo.delete(user);
    }

    public void updateUser(SiteUser updatedUser){
        SiteUser oldUser =  userRepo.findByUserid(updatedUser.getUserid());
        oldUser.setName(updatedUser.getName());
        oldUser.setBio(updatedUser.getBio());
        oldUser.setPassword(updatedUser.getPassword());
        oldUser.setImage(updatedUser.getImage());
        oldUser.setCareer(updatedUser.getCareer());
        oldUser.setDateOfBirth(updatedUser.getDateOfBirth());
        userRepo.save(oldUser);

    }



}
