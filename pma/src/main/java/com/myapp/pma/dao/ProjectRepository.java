package com.myapp.pma.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.myapp.pma.entities.Project;

// JAP에서는 리파지토리에 CRUD 가능한 Interface CrudRepository를 상속받음

public interface ProjectRepository extends CrudRepository<Project, Long>{ // (프로젝트, 아이디타입)
	// CrudRepository에 이미 CRUD코드가 다 만들어져 있음 => JPA 하이버네이트가 구현 코드도 다 자동생성
	@Override
	List<Project> findAll(); // 기존의 findAll()은 return타입이 Iterable<Project>이라서 수정

	Project findByProjectId(long id);
}
