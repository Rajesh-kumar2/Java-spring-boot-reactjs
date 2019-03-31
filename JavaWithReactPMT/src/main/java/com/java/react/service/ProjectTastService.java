package com.java.react.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.react.domain.Backlog;
import com.java.react.domain.ProjectTask;
import com.java.react.exception.ProjectNotFoundException;
import com.java.react.repository.ProjectTaskRepository;

@Service
public class ProjectTastService {

	/*
	 * @Autowired private BacklogRepository backlogRepository;
	 */

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	/*
	 * @Autowired private ProjectRepository projectRepository;
	 */

	@Autowired
    private ProjectService projectService;


     public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username){

		// Exceptions: Project not found

		try {
			// PTs to be added to a specific project, project != null, BL exists
			//Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
			 Backlog backlog =  projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog(); 
			// set the bl to pt
			projectTask.setBacklog(backlog);
			// we want our project sequence to be like this: IDPRO-1 IDPRO-2 ...100 101
			Integer BacklogSequence = backlog.getPTSequence();
			// Update the BL SEQUENCE
			BacklogSequence++;
			backlog.setPTSequence(BacklogSequence);
			// Add Sequence to Project Task
			projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			// INITIAL priority when priority null

			if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
				projectTask.setPriority(3);
			}

			// INITIAL status when status is null
			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}
			return projectTaskRepository.save(projectTask);
		} catch (Exception e) {
			throw new ProjectNotFoundException("Project Not Found");
		}
	}

	public Iterable<ProjectTask> findBacklogById(String backlog_id, String username) {
		
		projectService.findProjectByIdentifier(backlog_id, username);
		/*
		 * Project project = projectRepository.findByProjectIdentifier(backlog_id); if
		 * (project == null) { throw new ProjectNotFoundException("Project with id: '" +
		 * backlog_id + "' does not exist"); }
		 */

		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}

	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id,  String username) {

		// make sure we are searching on an existing backlog
		projectService.findProjectByIdentifier(backlog_id, username);
		/*
		 * Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id); if
		 * (backlog == null) { throw new ProjectNotFoundException("Project with id: '" +
		 * backlog_id + "' does not exist"); }
		 */
		// make sure that our task exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if (projectTask == null) {
			throw new ProjectNotFoundException("Project task '" + pt_id + "' not found");
		}
		// make sure that the backlog/project id in the path corresponds to the right
		// project
		if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project task '" + pt_id + "' does not exist in project '" + backlog_id);
		}

		return projectTask;

	}

	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id,  String username) {
		// Update project task
		// find existing project task
		// replace it with updated task
		// save update
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

		projectTask = updatedTask;

		return projectTaskRepository.save(projectTask);
	}

	public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

		/*
		 * Backlog backlog = projectTask.getBacklog(); List<ProjectTask> pts =
		 * backlog.getProjectTasks(); pts.remove(projectTask);
		 * backlogRepository.save(backlog);
		 */

		projectTaskRepository.delete(projectTask);

	}

}
