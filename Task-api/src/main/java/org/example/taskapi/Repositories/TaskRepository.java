package org.example.taskapi.Repositories;

import org.example.taskapi.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository <Task, Long>{

    @Query("SELECT t FROM Task t WHERE t.projectName = :projectId")
    List<Task> findByProjectId(@Param("projectId") String projectId);

    List<Task> findByAssigneeIgnoreCase(String assignee);
}
