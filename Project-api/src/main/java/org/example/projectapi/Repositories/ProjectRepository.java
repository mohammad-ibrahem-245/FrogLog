package org.example.projectapi.Repositories;

import org.example.projectapi.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByName(String name);

    Project findByName(String name);

    @Query("SELECT p FROM Project p WHERE p.owner = :user OR :user IN elements(p.members)")
    List<Project> findByOwnerOrMember(@Param("user") String user);




}
