package com.myapp.pma.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
//import javax.persistence.OneToMany;


@Entity // 이 클래스는 DB에 있는 테이블과 Mapping할 거라고 Spring에게 알려줌
public class Project {
	
	@Id // projectId를 기본키라고 설정한 것
	@GeneratedValue(strategy = GenerationType.IDENTITY) // (AUTO) : DB에서가 아니라 JPA Hibernate로 자동으로 id가 +1 됨
	private Long projectId; // h2 DB 테이블에서는 project_id로 이름이 변경됨(CamelCase => DB project_id)
	private String name;
	private String stage;
	private String description;
	
	// DB가 아니 JPA에서 관계설정
	//@OneToMany(mappedBy = "project")  1:n 관계 프로젝트는 하나 직원은 여러명, 프로젝트 테이블에 mapping(?) = 프로젝트 테이블에서 employee테이블을 참조할 수 있다는 뜻(?)
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, 
            }, fetch = FetchType.LAZY)
	@JoinTable(name = "project_employee", joinColumns = @JoinColumn(name="project_id"),
	inverseJoinColumns = @JoinColumn(name="employee_id")) // 해당 클래스의 db컬럼인 joincolumn, 다른 클래스의 db컬럼이 inversejoincolumn
	private List<Employee> employees; // 직원이 여러명이라서 list로 선언
	
	public Project() {
		// 빈 생성자
	}

	// projectID는 DB에서 자동생성 할 거라서 뺌
	public Project(String name, String stage, String description) {
		super();
		this.name = name;
		this.stage = stage;
		this.description = description;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	// 프로젝트 객체에서 직원을 추가하는 리스트
	public void addEmployee(Employee emp) {
		if(employees == null) { // 해당 프로젝트 객체에 없는 직원이라면 직원을 추가해줌(?)
			employees = new ArrayList<>();
		}
		employees.add(emp);
	}
}
