package com.myapp.pma.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.pma.dao.ProjectRepository;
import com.myapp.pma.entities.Project;

@Service
public class ProjectService {
	
	// 스프링에서 repository 객체를 처음에 자동생성해서 가지고 있다가 Autowired를 어노테이션하면 관련 객체를 필요할 때 자동으로 연결시켜줌
	@Autowired 
	private ProjectRepository projectRepository;

	public List<Project> findAll() {
		return projectRepository.findAll();
	}

	public void save(Project project) {
		projectRepository.save(project);
	}
}
