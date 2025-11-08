package org.example.projectapi.DTO;


import lombok.Data;

@Data
public class ProjectLeave {
    String projectName;
    String memberId;


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
