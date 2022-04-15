package com.project.experience.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.experience.dao.TodoRepository;
import com.project.experience.entities.Todo;

@Service
public class SearchService {

	@Autowired
	private TodoRepository todoRepo;
	
	@Transactional 
	public List<Todo> search(String keyword) { 
		List<Todo> todoSearch = todoRepo.findByTitleContaining(keyword);
		
		return todoSearch; 
	}
	

}
