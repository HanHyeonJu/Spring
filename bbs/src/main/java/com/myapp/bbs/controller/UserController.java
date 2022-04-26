package com.myapp.bbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myapp.bbs.dao.UserMapper;
import com.myapp.bbs.model.User;

@Controller
public class UserController {

	@Autowired
	private UserMapper userMapper;
	
	@GetMapping("/register")
	public String getUserView(@ModelAttribute User user) { // @ModelAttribute를 통해 register에 user 객체를 model로 전달
		return "/register";
	}
	
	
	@PostMapping("/register") 
	public String postUser(User user, RedirectAttributes attr) {
		// 클라이어트 단 또는 서버 단에서 데이터 요효성 체크를 권장
		
		User duplicatedUser = userMapper.selectByEmail(user.getEmail()); // 이메일이 같은 유저가 있는지 체크
		
		if(duplicatedUser == null) { // 같은 이메일이 없으면
			userMapper.insert(user);
			attr.addFlashAttribute("message", "가입되었습니다");
			return "redirect:/login"; 
		} else {
			// 이메일 중복
			attr.addFlashAttribute("message", "이메일 중복입니다");
			return "redirect:/register";
		}
		
	}
}
