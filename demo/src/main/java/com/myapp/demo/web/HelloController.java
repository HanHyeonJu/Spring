package com.myapp.demo.web; // main패키지 아래에 패키지를 생성 후 클래스를 만들어야 작동함 밖에서 클래스를 생성하면 작동하지 않음

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //어노테이션을 추가하면 관련 패키지도 함께 추가됨(Spring에서는 어노테이션 많이 사용)
@RequestMapping("/hello")
public class HelloController {
	
	@GetMapping("/basic") // ("/") = 기본주소 // 겟메소드
	public String sayHello() {
		return "<h1>헬로우월드</h1>";
	}
	
	@GetMapping("/korean")
	public String translate() {
		return "<h1>안녕하세요</h1>";
	}
	
	@GetMapping("/japan")
	public String translate2() {
		return "<h1>곤니찌와</h1>";
	}
	
	@GetMapping("/form")
	public String form() {
		return "<form method=\"post\" action=\"/hello/formpost\">\r\n"
				+ "    이름 : <input type=\"text\" name=\"name\"><br>\r\n"
				+ "    학번 : <input type=\"text\" name=\"id\"><br>\r\n"
				+ "    학과 : <input type=\"text\" name=\"dep\"><br>\r\n"
				+ "    <input type=\"submit\">\r\n"
				+ "</form>";
	}
	
	@PostMapping("/formpost")
	public String formpost( @RequestParam("name") String name,
							@RequestParam("id") String id,
							@RequestParam("dep") String dep) {
		return "당신의 이름은 " + name + " 아이디는 " + id + " 학과는 " + dep;
	}
	
	@GetMapping("/orders/{id}") // {} => 패스파라메터, id는 변수라고 생각하면 됨 (/orders/2를 입력하면 주문아이디는 2라고 출력 됨)
	public String order(@PathVariable String id) {
		return "주문 아이디는 " + id;
	}
}
