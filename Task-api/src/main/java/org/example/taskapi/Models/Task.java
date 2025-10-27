package org.example.taskapi.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.taskapi.Enums.Priority;
import org.example.taskapi.Enums.TaskStatus;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String projectName;
    private String description;
    private TaskStatus status;
    @Column(nullable = false)
    private Priority priority;
    @Column(nullable = false)
    private LocalDateTime dueDate;
    @Column(nullable = false)
    private String assignee;
    private LocalDateTime createdDate;
    private boolean edited ;

}
