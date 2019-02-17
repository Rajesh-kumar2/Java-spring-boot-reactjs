package com.java.react.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.react.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{
	
	public Project findByProjectIdentifier(String projectId);
	
	public List<Project> findAll();
}
