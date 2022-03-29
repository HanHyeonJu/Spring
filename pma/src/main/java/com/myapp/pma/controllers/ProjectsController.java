package com.myapp.pma.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;

import com.myapp.pma.entities.Employee;
import com.myapp.pma.entities.Project;
import com.myapp.pma.services.EmployeeService;
import com.myapp.pma.services.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectsController {
	
	
	@Autowired 
	private ProjectService projectService; // 인터페이스 객체 선언
	
	@Autowired 
	private EmployeeService employeeService;
	
	@GetMapping
	public String displayEmpList(Model model) {
		List<Project> projectList = projectService.findAll();
		model.addAttribute(projectList);
		return "projects/projectList";
	}
	
	@GetMapping("/new")
	public String displayProjectForm(Model model) {
		Project p = new Project();
		model.addAttribute("project", p);
		List<Employee> empList = employeeService.findAll();
		model.addAttribute("empList", empList);
		return "projects/new-project";
	}
	
	@PostMapping("/save")
	public String createProject(Project project) { // @RequestParam("employees") List<Long> ids employees는 리스트이기 때문에 List로 id를 받음
		projectService.save(project); // DB에 project객체를 테이블에 저장(post로 save에 갔을 때)
		return "redirect:/projects/new"; // (/save)post - redirect - (/new)get 패턴(PRG - 중복방지, 공유목적) 
	}
	// 이미 employee, project클래스에서 각각 클래스 DB의 리스트들을 조인한 상태로 새로운 테이블을 생성하였기 때문에 save를 하게 되면 별다른 설정을 하지 않아도 새로 생성한 테이블에 생성해 놓은 컬럼의 값들이 DB에 저장된다
	// 기존에 있는 테이블의 외래설정만으로는 n:n관계를 설정할 수 없기 때문에 새로운 테이블 생성(n:n관계를 가진 테이블을 통해 어떤 직원이 무슨 프로젝트들을 하고 있는지 어떤 프로젝트를 몇 명의 직원들이 하고 있는지 쉽게 알 수 있음)
	
	
	/* 프로젝트 생성시 선택된 직원들의 리스트를 가져옴(param으로 받은 id값을 가진 직원)
			Iterable은 순서가 있는 map, list들은 다 받을 수 있는 인터페이스 => for문을 사용하기 위해서 Iterable로 받음
			Iterable<Employee> selectEmployees = employeeRepository.findAllById(ids);
			 for 반복문을 이용
			for(Employee emp : selectEmployees) {
				emp.setProject(project); // 선택된 직원들에게 각각 무슨 프로젝트에 선택되었는지 가져와줌
				employeeRepository.save(emp); // 가져온 프로젝트 id와 함께 다시 employee DB테이블에 저장
			}
	*/
}
