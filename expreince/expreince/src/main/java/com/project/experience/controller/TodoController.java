package com.project.experience.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.experience.dao.TodoRepository;
import com.project.experience.entities.Todo;

@Controller
@RequestMapping("/todos")
public class TodoController {

	@Autowired
	private TodoRepository todoRepo;
	
	@GetMapping
	public String todoList(Model model) {
		List<Todo> todoList = todoRepo.findAll();
		model.addAttribute("todoList",todoList);
		return "todos/todo-list";
	}
	
	@GetMapping("/new")
	public String create(@ModelAttribute Todo todo) {
		return "todos/todo-new";
	}
	
	@PostMapping("/new")
	public String create(@Valid Todo todo, BindingResult bindingResult, RedirectAttributes attr) {
		if(bindingResult.hasErrors()) return "todos/new";
		attr.addFlashAttribute("message", "성공적으로 카테고리가 저장됨");
		attr.addFlashAttribute("alertClass", "alert-success"); 
		
		return "redirect:/todos";
	}
}
