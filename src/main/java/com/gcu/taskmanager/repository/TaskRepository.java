package com.gcu.taskmanager.repository;

import com.gcu.taskmanager.models.Task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
	List<Task> findByStatus(String status);
}
