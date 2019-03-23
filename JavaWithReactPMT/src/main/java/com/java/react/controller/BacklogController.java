package com.java.react.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.react.domain.ProjectTask;
import com.java.react.service.MapValidationErrorService;
import com.java.react.service.ProjectTastService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
	
	@Autowired	
	private ProjectTastService projectTaskService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
														@PathVariable String backlog_id){
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if(errorMap!=null) return errorMap;
		
		ProjectTask projeTask1 = projectTaskService.addProjectTask(backlog_id, projectTask) ;
		return new ResponseEntity<ProjectTask>(projeTask1, HttpStatus.CREATED);
	}

	
	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask>getProjectBackLog(@PathVariable String backlog_id){
		
		return projectTaskService.findBacklogById(backlog_id);
		
	}
	
	@GetMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?>getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id){
		ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id, pt_id);
		
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}
}
