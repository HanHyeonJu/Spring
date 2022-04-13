package com.myapp.shoppingmall.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myapp.shoppingmall.dao.AdminRepository;
import com.myapp.shoppingmall.dao.UserRepository;
import com.myapp.shoppingmall.entities.Admin;
import com.myapp.shoppingmall.entities.User;

@Service
public class UserDatailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AdminRepository adminRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User user = userRepo.findByUsername(username);
		
		System.out.println(user);
		
		Admin admin = adminRepo.findByUsername(username);
		
		System.out.println(admin);
		
		if(admin != null) return admin;
		if(user != null) return user;
		
		throw new UsernameNotFoundException("유저 " + username + "이 없습니다");
	}
}
