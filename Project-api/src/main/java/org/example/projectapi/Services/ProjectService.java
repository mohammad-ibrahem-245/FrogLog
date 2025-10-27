package org.example.projectapi.Services;

import org.example.projectapi.DTO.ProjectLeave;
import org.example.projectapi.Model.Project;
import org.example.projectapi.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    public boolean createProject(Project project){
       if (!checkIfProjectExists(project.getName())){
           project.setCreatedDate(LocalDate.now());
        projectRepository.save(project);
       }else  {
           return false;
       }

       if (checkIfProjectExists(project.getName())){
           return true ;
       }else  {
           return false;
       }

    }

    public boolean deleteProject(String name){
        if(checkIfProjectExists(name)){
            projectRepository.delete(findProjectByName(name));
            return true;
        }

        return false;
    }

    public boolean editProject(Project project ,String name){
        if (checkIfProjectExists(name)){
            Project projectval = getProjectByName(name);
            projectval.setName(project.getName());
            projectval.setDescription(project.getDescription());


            projectRepository.save(projectval);
            return true;
        }
        return false;
    }

    public Project getProjectByName(String name){
        Optional<Project> project = Optional.ofNullable(projectRepository.findByName(name));
        if (project.isPresent()){
            return project.get();
        }
        return null;
    }

    public boolean addMemberToProject(ProjectLeave projectLeave){
        if (checkIfProjectExists(projectLeave.getProjectName())) {
            Project project = getProjectByName(projectLeave.getProjectName());
            project.getMembers().add(projectLeave.getMemberId());
            projectRepository.save(project);
            return true;
        }
        return false;

    }

    public boolean deleteMemberFromProject(ProjectLeave projectLeave){
        if (checkIfProjectExists(projectLeave.getProjectName())) {
            Project project = getProjectByName(projectLeave.getProjectName());
            project.getMembers().remove(projectLeave.getMemberId());
            projectRepository.save(project);
            return true;
        }
        return false;
    }

    public List<Project> getProjectsByOwnerOrMember(String name){
        return projectRepository.findByOwnerOrMember(name);
    }

/// /////////////////////
///
///
///
///
///
///
///
///
/// separation between controller methods and class methods
///
///
///
///
///
///
///
///
/// /////////////////////
    private boolean checkIfProjectExists(String project){

        return projectRepository.existsByName(project);
    }

    private Project findProjectByName(String name){
   return projectRepository.findByName(name);
    }
}
