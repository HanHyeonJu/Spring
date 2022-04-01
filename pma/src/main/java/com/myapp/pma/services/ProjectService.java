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
	
	public void update(Project project) {
		// DB에서 업데이트 할 프로젝트를 넣어줌
		Project pro = projectRepository.findByProjectId(project.getProjectId());
		// 필요한 내용만 업데이트
		pro.setName(project.getName());
		pro.setStage(project.getStage());
		pro.setDescription(project.getDescription());
		// 수정한 객체 emp를 저장
		projectRepository.save(pro);
	}

	public Project findByProjectId(long id) {
		return projectRepository.findByProjectId(id);
	}

	public void deleteProjectById(long id) {
		projectRepository.deleteById(id);
	}	
}
