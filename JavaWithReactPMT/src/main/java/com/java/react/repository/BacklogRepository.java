package com.java.react.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.react.domain.Backlog;

@Repository
public interface BacklogRepository extends JpaRepository<Backlog, Long> {
	
	Backlog findByProjectIdentifier(String projectIdentifier);
}