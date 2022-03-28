package com.myapp.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myapp.demo.domain.User;

@Controller // view 페이지를 리턴
public class HomeController {
	
	// 뷰와 컨트롤러 사이의 데이터를 Model을 통해 전달
	@GetMapping("/")
	public String home(Model model) {
		// "user"라는 이름에 User의 빈 객체를 넣어서 model에 추가 그리고 "user"를 model을 통해 index.html페이지에 전달
		model.addAttribute("user", new User());
		return "index"; // 컨트롤러로 어노테이션한 클래스에서 getMapping하면 index라는 String data를 return하는게 아니라 index라는 view를 찾음
	} // 빈 객체를 index페이지로 전달했기 때문에 값이 없음
	
	@PostMapping("/create") // post로 create주소로 이동하기만 해도 result페이지 보여줌
	public String processFormData(User user, RedirectAttributes attr) { // redirect로 데이터를 전달해주기위해 redirect객체 생성
		// System.out.println(user.toString());
		attr.addFlashAttribute("user", user);
		return "redirect:/display"; // display 페이지로 새로운 요청 => redirectr객체 없이는 redirect로 result페이지에 데이터가 전달이 안됨
		// index페이지에서 입력한 user객체의 값이 result페이지에서 보임
	    // return result";
	}   // locahlhost:8080 - index.html [값 입력후 submit버튼 클릭] => localhost:8080/create 이동 => [값이 들어있는 user객체가 있는]result.html이 보임
	
	@GetMapping("/display") // redirect로 display로 들어감
	public String displayFormDate(User user) {
		return "result"; // user데이터가 보이는 result페이지로 데이터가 redirect되어 result페이지가 return됨
	}// model을 통해서는 그냥 객체 전달이 가능하지만 redirect로 전달하는 경우는 redirect객체를 생성하여 전달해야함
}
