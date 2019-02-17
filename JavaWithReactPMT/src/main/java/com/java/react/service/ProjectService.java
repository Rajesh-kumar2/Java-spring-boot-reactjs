package com.java.react.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.react.domain.Project;
import com.java.react.exception.ProjectIdException;
import com.java.react.repository.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		} catch(Exception e){
			throw new ProjectIdException("Project Id '"+project.getProjectIdentifier().toUpperCase()+"' already exist");
		}
	}
	
	public Project findByProjectIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if(project == null) {
			throw new ProjectIdException("Project Id '"+ projectId.toUpperCase() +"' does not exist");
		}
		return project;
	}
	
	public List<Project> findAllProject(){
		List<Project> list = projectRepository.findAll();
		if(list.isEmpty() && list.size()==0) {
			throw new ProjectIdException("No Project exists in DB");
		}
		return list;
	}
	
	public void deleteProjectByIdentifier(String projectId) {
		Project project =projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if(project == null) {
			throw new ProjectIdException("Project Id '"+ projectId.toUpperCase() +"' does not exist to be deleted");
		}
		projectRepository.delete(project);
	}

}
