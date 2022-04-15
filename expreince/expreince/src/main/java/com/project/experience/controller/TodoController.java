package com.project.experience.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import com.project.experience.dao.SearchMapper;
import com.project.experience.dao.TodoRepository;
import com.project.experience.entities.Todo;
import com.project.experience.service.SearchService;

@Controller
@RequestMapping("/todos")
public class TodoController {

	@Autowired
	private TodoRepository todoRepo;
	
	@Autowired
	private SearchService service;
	
	@GetMapping
	public String todoList(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		
		int perPage = 5; 
		Pageable pageable = PageRequest.of(page, perPage);
		
		Page<Todo> todo = todoRepo.findAll(pageable);
		model.addAttribute("todo", todo);
		
		List<Todo> todoList = todoRepo.findAllByOrderByTargetDateDesc();
		model.addAttribute("todoList",todoList);
		
		long count = todoRepo.count();
		double pageCount = Math.ceil((double)count/(double)perPage);
		
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("perPage", perPage);
		model.addAttribute("count", count);
		model.addAttribute("page", page);
		
		return "todos/todo-list";
	}
	
	@GetMapping("/new")
	public String create(@ModelAttribute Todo todo) {
		return "todos/todo-new";
	}
	
	@PostMapping("/new")
	public String create(@Valid Todo todo, BindingResult bindingResult, RedirectAttributes attr) {
		
		if(bindingResult.hasErrors()) return "todos/todo-new";
		
		attr.addFlashAttribute("message", "성공적으로 저장되었습니다");
		attr.addFlashAttribute("alertClass", "alert-success"); 
		
		Todo todoExist = todoRepo.findByTitleAndIdNot(todo.getTitle(), todo.getId());
		
		if(todoExist != null) {
			attr.addFlashAttribute("message","존재하는 todo입니다");
			attr.addFlashAttribute("alertClass","alert-danger");
			attr.addFlashAttribute("todo", todo);
		} else {
			todoRepo.save(todo);
		}
	
		return "redirect:/todos/new"; 
	}
	
	@GetMapping("/edit/{id}")
	public String editTodo(@PathVariable int id, Model model) {
		Todo todo = todoRepo.getById(id);
		model.addAttribute("todo", todo);
		return "todos/todo-edit";
	}
	
	@PostMapping("/edit")
	public String editTodo(@Valid Todo todo, BindingResult bindingResult, RedirectAttributes attr) {
		
		if(bindingResult.hasErrors()) return "todos/todo-edit"; 
		
		attr.addFlashAttribute("message", "성공적으로 수정되었습니다");
		attr.addFlashAttribute("alertClass", "alert-success"); 
		
		Todo todoExist = todoRepo.findByTitleAndIdNot(todo.getTitle(), todo.getId());
		
		if(todoExist != null) {
			attr.addFlashAttribute("message","존재하는 todo입니다");
			attr.addFlashAttribute("alertClass","alert-danger");
			attr.addFlashAttribute("todo", todo);
		} else {
			todoRepo.save(todo);
		}
		
		return "redirect:/todos";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteTodo(@PathVariable int id, RedirectAttributes attr) {
		
		todoRepo.deleteById(id);
		attr.addFlashAttribute("message", "성공적으로 삭제 되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");	
		
		return "redirect:/todos";
	}
	
	
	@GetMapping("/search")
	public String searcgTodo(@RequestParam(value="keyword") String keyword, Model model) {
		
		List<Todo> todoSearch = service.search(keyword);
		model.addAttribute("todoSearch", todoSearch);
		
		System.out.println(todoSearch);
		return "todos/todo-search";
	}
}
