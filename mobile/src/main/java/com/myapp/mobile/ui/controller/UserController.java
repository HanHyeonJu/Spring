package com.myapp.mobile.ui.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.mobile.model.request.UpdateUserRequest;
import com.myapp.mobile.model.request.UserRequest;
import com.myapp.mobile.model.response.UserRest;

@RestController
@RequestMapping("/users") // localhost:8080/users
public class UserController {
	
	Map<String, UserRest> users;
	
	// 효율적으로 DB를 사용하기 위해 리스트 전체를 가져오지 않고 조건을 정해서 가져옴 (ex. 페이지 숫자와 한 페이지 당 가져올 유저숫자를 정한다)
	// defaultValue값을 설정해놓으면 pram을 작성하지 않아도 default값이 request된다
	@GetMapping
	public String getUserList(@RequestParam(value = "page", defaultValue = "1") int page,
							  @RequestParam(value="limit", defaultValue="50") int limit,
							  @RequestParam(value="sort", defaultValue = "DESC", required = false) String sort) { 
//		if(sort == null) sort = "DESC"; sort의 defaultValue를 설정하지 않았을 때
		return "유저리스트 리턴 : " + page + " 페이지당 유저수 : " + limit + " 정렬방법 : " + sort ;
	}
	
	// 기본적으로 json으로 return되지만 xml라이브러리를 설치해주고 produces를 이용해서 xml, json 각각 타입으로 리턴받을 수 있게 설정해줄 수 있음
	// postman에서 MediaType의 순서에 따라 맨 먼저 오는 타입을 기본으로 리턴함, 다른 타입으로 리턴받고 싶은 경우 header의 accept를 수정해준다(ex. Accept | application/xml)
	@GetMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, 
												MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<UserRest> getUser(@PathVariable("userId") String id) {
		// 자바 객체 유저를 리턴
		if(users.containsKey(id)) { // 유저아이디가 존재한다면
			// 객체를 return시 @RestController 클래스에서는 json으로 return 
			return new ResponseEntity<UserRest>(users.get(id), HttpStatus.OK); // 200
		} else {
			// 아이디가 없다
			return new ResponseEntity<UserRest>(HttpStatus.NO_CONTENT); // 204
		}
	}
	
	@GetMapping("/bad")
	public ResponseEntity<String> badRequest(){
		return new ResponseEntity<String>("잘못된 요청", HttpStatus.BAD_REQUEST); // 400
	}
	
	// postman요청은 header에 실제 데이터는 body에 있음
	
	@PostMapping
	public ResponseEntity<UserRest> getCreateUser(@Valid @RequestBody UserRequest user) {
		UserRest returnUser =  new UserRest();
		returnUser.setName(user.getName());
		returnUser.setEmail(user.getEmail());
		String userId = UUID.randomUUID().toString(); // UUID는 랜덤으로 아이디를 알아서 만들어줌
		returnUser.setUserId(userId);
		
		if(users == null) users =  new HashMap<>(); // 싱글턴 선언된 맵<문자열, UserRest>가 없으면 새로 생성
		users.put(userId, returnUser); // 유저id, 유저객체 쌍으로 입력
		
		return new ResponseEntity<UserRest>(returnUser, HttpStatus.CREATED); // 201
	}
	
	// 유저아이디로 해당 유저의 정보를 찾아서 UserRest를 받고 그 UserRest의 body에 수정할 이름을 json으로 입력
	@PutMapping("/{userId}")
	public UserRest getUpdateUser(@PathVariable("userId") String id, @Valid @RequestBody UpdateUserRequest user) {
		UserRest savedUser = users.get(id);
		savedUser.setName(user.getName()); // 이름만 수정됨
		
		return savedUser;
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> getDeleteUser(@PathVariable("userId") String id) {
		users.remove(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
