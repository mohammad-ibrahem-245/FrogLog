package org.example.projectapi.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.config.Task;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false , unique = true )
    private String name;
    private String description;
    @ElementCollection
    private List<String> members;
    private LocalDate createdDate;
    @Column(nullable = false)
    private String owner;
}
