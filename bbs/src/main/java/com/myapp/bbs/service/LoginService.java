package com.myapp.bbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.bbs.dao.UserMapper;
import com.myapp.bbs.model.Login;
import com.myapp.bbs.model.User;

@Service
public class LoginService {
	
	@Autowired
	private UserMapper userMapper;
	
	public void authenticate(Login login) {
		// 이메일로 검색해서 유저 찾기
		User user = userMapper.selectByEmail(login.getEmail());
		
		if(user == null) { // 검색 결과가 없는 경우
			login.setError("이메일이 존재하지 않습니다.");
		} else {
			if(!user.getPassword().equals(login.getPassword())) {
				login.setError("비밀번호가 틀립니다.");
			} else {
				login.setError(null); // 에러 없음 (로그인 인증됨)
			}
		}
	}
	
	public User findUserByEmail(String email) {
		// 이메일로 유저 정보 찾기
		User user = userMapper.selectByEmail(email);
		return user;
	}
	
}

