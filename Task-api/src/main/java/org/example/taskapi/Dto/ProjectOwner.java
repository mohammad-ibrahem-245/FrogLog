package org.example.taskapi.Dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
public class ProjectOwner {
    private String owner;
    private List<String> members;
}
