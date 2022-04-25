package com.myapp.bbs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.NoArgsConstructor;

@Data // get set toString
@AllArgsConstructor // 모든 필드변수로 생성자 생성
//@NoArgsConstructor // 기본생성자
public class User {
	
	private String email;
	private String password;
	private String name;
}
