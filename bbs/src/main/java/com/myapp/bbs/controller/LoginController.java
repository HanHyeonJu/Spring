package com.myapp.bbs.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.myapp.bbs.model.Login;
import com.myapp.bbs.model.User;
import com.myapp.bbs.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@GetMapping("/login")
	public String getLoginView(@ModelAttribute Login login) {
		// 로그인 뷰 페이지에 Login객체 login을 바인딩
		return "login";
	}
	
	/**
	 * 로그인 인증 처리 되었을 경우 세션에 유저 저장
	 * @param login
	 * @param model
	 * @param session
	 * @return
	 */
	@PostMapping("/login")
	public String postLogin(Login login, Model model, HttpSession session) {
		loginService.authenticate(login);
		
		if(login.getError() != null) {
			model.addAttribute("message", login.getError());
			return "login";
		} else {
			User user = loginService.findUserByEmail(login.getEmail());
			session.setAttribute("user", user); // 세션에 인증된 유저를 저장
			System.out.println(user.toString()); // user 출력
			return "redirect:/board/list";
		}
	}
}
