package com.java.react.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.react.domain.Backlog;
import com.java.react.domain.Project;
import com.java.react.domain.User;
import com.java.react.exception.ProjectIdException;
import com.java.react.exception.ProjectNotFoundException;
import com.java.react.repository.BacklogRepository;
import com.java.react.repository.ProjectRepository;
import com.java.react.repository.UserRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private UserRepository userRepository;

	public Project saveOrUpdateProject(Project project, String username) {
		if (project.getId() != null) {
			Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
			if (existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
				throw new ProjectNotFoundException("Project not found in your account");
			} else if (existingProject == null) {
				throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier()
						+ "' cannot be updated because it doesn't exist");
			}
		}

		try {
			User user = userRepository.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			if (project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			} else {
				project.setBacklog(
						backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException(
					"Project Id '" + project.getProjectIdentifier().toUpperCase() + "' already exist");
		}
	}

	public Project findProjectByIdentifier(String projectId, String username) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if (project == null) {
			throw new ProjectIdException("Project Id '" + projectId.toUpperCase() + "' does not exist");
		}

		if (!project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("Project not found in your account");
		}
		return project;
	}

	public List<Project> findAllProject(String username) {
		List<Project> list = projectRepository.findAllByProjectLeader(username);
		if (list.isEmpty() && list.size() == 0) {
			throw new ProjectIdException("No Project exists in DB");
		}
		return list;
	}

	public void deleteProjectByIdentifier(String projectId, String username) {
		/*
		 * Project project
		 * =projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		 * if(project == null) { throw new ProjectIdException("Project Id '"+
		 * projectId.toUpperCase() +"' does not exist to be deleted"); }
		 */
		projectRepository.delete(findProjectByIdentifier(projectId, username));
	}

}
