package org.example.userapi.Repositories;

import org.example.userapi.Model.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<SiteUser,Long> {

    SiteUser findByUserid (String userid);

    @Query(value = "SELECT * FROM site_user ORDER BY RANDOM() LIMIT 6", nativeQuery = true)
    Optional<List<SiteUser>> findRandomUser();

    boolean existsByUserid(String userid);





}
