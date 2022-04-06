package com.project.todo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.todo.entities.Todo;

public interface TodoRepository  extends JpaRepository<Todo, Long>{

}
