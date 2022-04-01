package com.myapp.pma.entities;

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
//import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Employee {
	
	@Id // 기본키를 명시
	@GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 자동생성
	private Long employeeId;
	
	@NotBlank(message = "이름을 입력해주세요")
	@Size(min=1, max=20, message="이름은 1자에서 20자까지 가능합니다")
	private String firstName;
	
	@NotBlank(message = "성을 입력해주세요")
	@Size(min=1, max=3, message="성은 1자에서 3자까지 가능합니다")
	private String lastName;
	
	@NotBlank(message = "이메일을 입력해주세요")
	private String email;
	
	// DB가 아니 JPA에서 관계설정
	/*@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, 
            CascadeType.PERSIST}, fetch = FetchType.LAZY) n:1 관계 직원은 여러 명 프로젝트는 하나*/
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY) // n:n 관계
	//@JoinColumn(name = "project_id") // 직원 테이블에 project_id 외래키 열을 추가
	@JoinTable(name = "project_employee", joinColumns = @JoinColumn(name="employee_id"),
				inverseJoinColumns = @JoinColumn(name="project_id")) // n:n 관계에서는 새로운 테이블을 만들고 해당 클래스의 테이블에 id, 다른 테이블의 id를 입력
	
	//private Project project;
	@JsonIgnore // restful할 때 -- ?
	private List<Project> projects;
	
	public Employee() {
		
	}

	public Employee(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	

//	public Project getProject() {
//		return project;
//	}
//
//	public void setProject(Project project) {
//		this.project = project;
//	}
	
	
}
