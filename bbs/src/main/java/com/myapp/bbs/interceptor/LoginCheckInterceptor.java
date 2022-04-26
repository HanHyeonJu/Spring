package com.myapp.bbs.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.myapp.bbs.model.User;

/* 새 인터셉터 클래스를 만들고 인터페이스 HandlerInterceptor를 구현하여 인터셉터 만들기*/
/* 로그인 한 후 계속해서 인증 상태인지 확인 (컨트롤러를 통해 이동하는 페이지들 모두 확인가능)*/
public class LoginCheckInterceptor implements HandlerInterceptor{
	/* preHandler메소드 : 컨트롤러에 가기 전 Interceptor에서 캐치해서 작업 수행 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession(); // 세션에 저장한 user 객체 불러오기
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			// 세션에서 가져온 유저가 없을 경우(인증 안 된 상태) = false
			// 로그인 페이지로 이동
			String url = session.getServletContext().getContextPath() + "/login";
			response.sendRedirect(url);
			System.out.println("LoginInterceptor # preHandle() : NO PASS");
			return false;
		}
		
		System.out.println("LoginInterceptor # preHandle() : PASS");
		// 인터셉터 메소드에서 리턴이 true면 통과 false면 차단
		return true;
	}
}
