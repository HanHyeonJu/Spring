package com.myapp.pma.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.myapp.pma.dao.EmployeeRepository;
import com.myapp.pma.entities.Employee;
import com.myapp.pma.services.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
//	@Autowired 
//	private EmployeeRepository employeeRepository;
	
	@Autowired 
	private EmployeeService employeeService;
	
	@GetMapping
	public String displayEmpList(Model model) {
		List<Employee> employeeList = employeeService.findAll();
		model.addAttribute(employeeList);
		return "employees/empList";
	}

	@GetMapping("/new")
	public String displayEmployeeForm(Model model) {
		Employee e = new Employee();
		model.addAttribute("employee", e);
		return "employees/new-employee";
	}
	
	@PostMapping("/save") // 저장할 때 아이디가 있으면 업데이트 없으면 새로 만들기
	public String createProject(@Valid Employee employee, Errors errors) { // 메소드에 객체가 넘어올 때 유효성 검사함
		// errors는 유효성검사 관련 객체
		if(errors.hasErrors()) return "employees/new-employee"; // 유효성 검사에 통과하지 못한 경우 입력페이지로 되돌림
		
		Long id = employee.getEmployeeId();
		if(id != null) {
			employeeService.update(employee);
		} // if문이 없는데 업데이트가 된 건 뭐지?
		else{
			employeeService.save(employee); //DB에 employee객체를 테이블에 저장
		}
		return "redirect:/employees"; //post-redirect-get 패턴
	}
	
	@GetMapping("/update")
	public String displayEmployeeUpdateForm(@RequestParam("id") long id, Model model) {
		// id로 DB에서 업데이트할 직원을 찾아서 매핑하여 화면에 표시하기, 값이 있는 직원 객체
		Employee employee = employeeService.findByEmployeeId(id); //DB에서 찾기
		model.addAttribute("employee", employee);
		return "employees/new-employee";
	}
	
	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam("id") long id, Model model) {
		employeeService.deleteEmployeeById(id);
		return "redirect:/employees";
	}
}
