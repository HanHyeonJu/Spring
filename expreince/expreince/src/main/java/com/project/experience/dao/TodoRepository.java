package com.project.experience.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.experience.entities.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer>{

	Todo findByTitleAndIdNot(String title, int id);

	List<Todo> findAllByOrderByTargetDateDesc();

	List<Todo> findByTitleContaining(String keyword);

}
