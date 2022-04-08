package com.project.experience.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.experience.entities.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer>{

}
