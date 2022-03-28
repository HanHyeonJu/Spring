package com.myapp.demo.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.demo.domain.Product;


@RestController
@RequestMapping("/user") // http://localhost:8080/user
public class UserController {
	
	@GetMapping("/{id}")
	public String userID(@PathVariable("id") String id) {
		return "유저아이디 : " + id;
	}
	
	@GetMapping("/{id}/contact")
	public String displayContact(@PathVariable("id") String id,
								 @RequestParam(value = "phone", defaultValue = "연락처 없음") String phone) {
		return "유저 아이디 : " + id + " 연락처 : " + phone;
	}
	
	// 리스트를 리턴 => 제이슨 타입으로 보여짐
	@GetMapping("/{id}/items")
	public List<String> userItems() {
		return Arrays.asList("가방", "노트북", "신발");
	} 
	
	// 객체를 리턴 => 제이슨
	@GetMapping("/{id}/products")
	public List<Product> products() {
		return Arrays.asList(new Product(1, "모자", 5000),
							 new Product(2, "신발", 7000),
							 new Product(3, "가방", 8000));
	} 
}
