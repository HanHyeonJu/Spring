package com.myapp.shoppingmall.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myapp.shoppingmall.dao.CategoryRepository;
import com.myapp.shoppingmall.entities.Category;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@GetMapping
	public String index(Model model) {
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		return "admin/categories/index";
	}
	
	@GetMapping("/add")
	public String add(@ModelAttribute Category category) { // Model model 코드 줄임
		return "admin/categories/add"; // page객체가 add.html에 바인딩 됨(?)
	}
	
	@PostMapping("/add")
	public String add(@Valid Category category,BindingResult bindingResult, RedirectAttributes attr) { // model 대신 redirectattributes 사용
		// 유효성 검사 에러가 있으면 다시 돌아감
		if(bindingResult.hasErrors()) return "admin/categories/add";
		// 검사 통과시
		attr.addFlashAttribute("message", "성공적으로 카테고리가 저장됨");
		attr.addFlashAttribute("alertClass", "alert-success"); // 부트스트랩 클래스 - 경고창(성공색)
		
		
		String slug = category.getName().toLowerCase().replace(" ", "-");
		Category nameExist = categoryRepo.findByName(category.getName()); // 이름이 DB에 있으면 category로 리턴
		
		if(nameExist != null) { // 같은 이름의 카테고리가 있는경우
			attr.addFlashAttribute("message","존재하는 카테고리입니다");
			attr.addFlashAttribute("alertClass","alert-danger");
			attr.addFlashAttribute("category",category);
		} else {
			category.setSlug(slug); // 소문자나 -으로 수정된 slug update
			category.setSorting(100); // 기본 sorting값
			categoryRepo.save(category);
		}
		
		
		return "redirect:/admin/categories/add"; // post - redirect -get => redirect 하는 이유: 새로고침했을 때 postMapping이 한번 더 실행되는 것 방지 redirect는 model사용 못 함 
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		Category category = categoryRepo.getById(id); // 테이블에서 id로 page 검색
		model.addAttribute("category",category); // 수정페이지에 page 정보객체를 전달
		return "admin/categories/edit";
	}
	
	@PostMapping("/edit")
	public String edit(@Valid Category category,BindingResult bindingResult, RedirectAttributes attr) { // model 대신 redirectattributes 사용
		// 유효성 검사 에러가 있으면 다시 돌아감
		if(bindingResult.hasErrors()) return "admin/categories/edit";
		// 검사 통과시
		attr.addFlashAttribute("message", "성공적으로 카테고리가 수정됨");
		attr.addFlashAttribute("alertClass", "alert-success"); // 부트스트랩 클래스 - 경고창(성공색)
		
		
		String slug = category.getName().toLowerCase().replace(" ", "-");
		Category nameExist = categoryRepo.findByName(category.getName()); // 이름이 DB에 있으면 category로 리턴
		
		if(nameExist != null) { // 같은 이름의 카테고리가 있는경우
			attr.addFlashAttribute("message","존재하는 카테고리입니다");
			attr.addFlashAttribute("alertClass","alert-danger");
			attr.addFlashAttribute("category",category);
		} else {
			category.setSlug(slug); // 소문자나 -으로 수정된 slug update
			category.setSorting(100); // 기본 sorting값
			categoryRepo.save(category);
		}
		
		
		return "redirect:/admin/categories/edit/" + category.getId(); // post - redirect -get => redirect 하는 이유: 새로고침했을 때 postMapping이 한번 더 실행되는 것 방지 redirect는 model사용 못 함 
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes attr) {
		categoryRepo.deleteById(id);
		attr.addFlashAttribute("message", "성공적으로 삭제 되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");		
		
		return "redirect:/admin/categories";
	}
	
	@PostMapping("/reorder")
	public @ResponseBody String reorder(@RequestParam("id[]") int[] id) { // [] 배열
		int count = 1;
		Category category;
		
		for(int categoryId : id) {
			category = categoryRepo.getById(categoryId); // DB에서 id로 page객체 검색
			category.setSorting(count);
			categoryRepo.save(category); // sorting값을 넣고 저장한다
			count++;
		}
		
		return "ok"; // view페이지가 아니고 ok문자열로 return
	}
}
