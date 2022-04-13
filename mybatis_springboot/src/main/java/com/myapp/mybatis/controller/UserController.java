package com.myapp.mybatis.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.mybatis.mapper.UserMapper;
import com.myapp.mybatis.model.User;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserMapper  userMapper;
	
	@GetMapping("/{id}")
	public User getUser(@PathVariable("id") String id){
        // User user = DB에 연결할 매퍼(=repository).메소드(id); 매퍼에서 만들어 놓은 쿼리문이 실행됨
        User user = userMapper.getUser(id);
        return user;
	}
	
	@GetMapping
	public List<User> getUserList(){
	   List<User> userList = userMapper.getUserList(); 
	   return userList;
	}
	
	@PostMapping 
	public void createUser(@RequestParam("id") String id,
		     			   @RequestParam("name") String name,
		     			   @RequestParam("phone") String phone,
                           @RequestParam("address") String address) { // 매개변수를 User user로한 경우
		// userMapper.insertUser(user.getId(), user.getName(), user.getPhone(), user.getAddress())
		userMapper.insertUser(id, name, phone, address);
	}
	
	@PutMapping("/{id}")
	public void editUser( @PathVariable("id") String id,
			   			  @RequestParam("name") String name,
			   			  @RequestParam("phone") String phone,
			   			  @RequestParam("address") String address) {
		// int i = userMapper.updateUser(id, name, phone, address); => jsp랑 마찬가지로 update 성공하면 1
		userMapper.updateUser(id, name, phone, address);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") String id) {
		userMapper.delteUser(id);
	}
	
}
