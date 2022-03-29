package com.myapp.pma.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@PostMapping("/save")
	public String createEmployee(Employee employee) {
		employeeService.save(employee); // DB에 project객체를 테이블에 저장(post로 save에 갔을 때)
		return "redirect:/employees/new"; // (/save)post - redirect - (/new)get 패턴
	}
}
