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

import com.myapp.shoppingmall.dao.PageRepository;
import com.myapp.shoppingmall.entities.Page;

@Controller
@RequestMapping("/admin/pages")
public class AdminPageController {

	@Autowired
	private PageRepository pageRepo;
	
	@GetMapping
	public String index(Model model) {
		List<Page> pages = pageRepo.findAllByOrderBySortingAsc();
		model.addAttribute("pages", pages);
		return "admin/pages/index";
	}
	
	@GetMapping("/add")
	public String add(@ModelAttribute Page page) { // Model model 코드 줄임
		return "admin/pages/add"; // page객체가 add.html에 바인딩 됨(?)
	}
	
	@PostMapping("/add")
	public String add(@Valid Page page,BindingResult bindingResult, RedirectAttributes attr) { // model 대신 redirectattributes 사용
		// 유효성 검사 에러가 있으면 다시 돌아감
		if(bindingResult.hasErrors()) return "admin/pages/add";
		// 검사 통과시
		attr.addFlashAttribute("message", "성공적으로 페이지 수정됨");
		attr.addFlashAttribute("alertClass", "alert-success"); // 부트스트랩 클래스 - 경고창(성공색)
		
		// 슬러그 검사(슬러그 미입력시 제목을 소문자로 공백은 - 으로 대체, 입력시에도 소문자 공백은 - 대체)
		// 조건 ? true : false
		String slug = (page.getSlug() == "") ? page.getTitle().toLowerCase().replace(" ", "-") : page.getSlug();
		Page slugExist = pageRepo.findBySlug(slug); // 슬러그가 DB에 있으면 page로 리턴
		
		if(slugExist != null) {
			attr.addFlashAttribute("message","슬래그가 이미 있습니다");
			attr.addFlashAttribute("alertClass","alert-danger");
			attr.addFlashAttribute("page",page);
		} else {
			page.setSlug(slug); // 소문자나 -으로 수정된 slug update
			page.setSorting(100); // 기본 sorting값
			pageRepo.save(page);
		}
		
		
		return "redirect:/admin/pages/add"; // post - redirect -get => redirect 하는 이유: 새로고침했을 때 postMapping이 한번 더 실행되는 것 방지 redirect는 model사용 못 함 
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		Page page = pageRepo.getById(id); // 테이블에서 id로 page 검색
		model.addAttribute("page", page); // 수정페이지에 page 정보객체를 전달
		return "admin/pages/edit"; // 수정 페이지
	}
	
	@PostMapping("/edit")
	public String edit(@Valid Page page,BindingResult bindingResult, RedirectAttributes attr) { // model 대신 redirectattributes 사용
		// 유효성 검사 에러가 있으면 다시 돌아감
		if(bindingResult.hasErrors()) return "admin/pages/edit";
		// 검사 통과시
		attr.addFlashAttribute("message", "성공적으로 페이지 추가됨");
		attr.addFlashAttribute("alertClass", "alert-success"); // 부트스트랩 클래스 - 경고창(성공색)
		
		// 슬러그 검사(슬러그 미입력시 제목을 소문자로 공백은 - 으로 대체, 입력시에도 소문자 공백은 - 대체)
		// 조건 ? true : false
		String slug = (page.getSlug() == "") ? page.getTitle().toLowerCase().replace(" ", "-") : page.getSlug();
		Page slugExist = pageRepo.findBySlugAndIdNot(slug, page.getId()); // 슬러그가 DB에 있으면 page로 리턴(현재 아이디의 슬러그는 제외)
		
		if(slugExist != null) {
			attr.addFlashAttribute("message","슬래그가 이미 있습니다");
			attr.addFlashAttribute("alertClass","alert-danger");
			attr.addFlashAttribute("page",page);
		} else {
			page.setSlug(slug); // 소문자나 -으로 수정된 slug update
			page.setSorting(100); // 기본 sorting값
			pageRepo.save(page);
		}
		
		
		return "redirect:/admin/pages/edit/" + page.getId(); // post - redirect -get => redirect 하는 이유: 새로고침했을 때 postMapping이 한번 더 실행되는 것 방지 redirect는 model사용 못 함 
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes attr) {
		pageRepo.deleteById(id);
		attr.addFlashAttribute("message", "성공적으로 삭제 되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");		
		
		return "redirect:/admin/pages";
	}
	
	@PostMapping("/reorder")
	public @ResponseBody String reorder(@RequestParam("id[]") int[] id) { // [] 배열
		int count = 1;
		Page page;
		
		for(int pageId : id) {
			page = pageRepo.getById(pageId); // DB에서 id로 page객체 검색
			page.setSorting(count);
			pageRepo.save(page); // sorting값을 넣고 저장한다
			count++;
		}
		
		return "ok"; // view페이지가 아니고 ok문자열로 return
	}
}
