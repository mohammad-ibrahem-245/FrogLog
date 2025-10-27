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



    /// searching for user
    public Optional<SiteUser> findUser(String username) {
        Optional<SiteUser> user = Optional.ofNullable(userRepo.findByUserid(username));
        return user;
    }



    ///finding six random users for friendship suggetsion
    public Optional<List<SiteUser>> findRandomUser(){
        Optional<List<SiteUser>> user = userRepo.findRandomUser();
        return user;
    }



    /// signing up a new user
    public boolean saveUser(SiteUser user){
        if (!checkUserExists(user.getUserid())) {

            user.setPassword(passwordHasher.hashToString(12, user.getPassword().toCharArray()));
            user.setCreated(Date.from(new Date().toInstant()));
            userRepo.save(user);
            if (findUser(user.getUserid()).isPresent()) {
                return true;
            }
        }
        return false ;
    }



    /// deleting a user account
    public boolean deleteUser(String user){
        if (checkUserExists(user)) {
            findUser(user).ifPresent(userRepo::delete);
            return true;
        }
        return false;
    }



    /// updating user data
    public boolean updateUser(SiteUser updatedUser){
        if (checkUserExists(updatedUser.getUserid())) {
            SiteUser oldUser = userRepo.findByUserid(updatedUser.getUserid());
            oldUser.setName(updatedUser.getName());
            oldUser.setBio(updatedUser.getBio());
            oldUser.setPassword(updatedUser.getPassword());
            oldUser.setImage(updatedUser.getImage());
            oldUser.setCareer(updatedUser.getCareer());
            oldUser.setDateOfBirth(updatedUser.getDateOfBirth());
            userRepo.save(oldUser);
            return true;
        }
        return false;

    }



    /// internal usage only !! used to check the existence of a user
    private boolean checkUserExists(String user){

        return userRepo.existsByUserid(user);

    }



}
