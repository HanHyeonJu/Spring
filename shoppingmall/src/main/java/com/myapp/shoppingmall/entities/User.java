package com.myapp.shoppingmall.entities;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails{ // UserDetails?

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank
	@Size(min = 2, message = "유저네임은 최소 2자 이상")
	private String username;
	
	@NotBlank
	@Size(min = 4, message = "패스워드는 최소 4자 이상")
	private String password;
	
	@Transient
	private String confirmPassword; // 패스워드 확인시 사용, 실제 테이블에는 존재하지 않음
	
	@Email(message = "이메일 양식에 맞게 입력")
	private String email;
	
	@Column(name="phone_number")
	@Size(min = 7, message = "전화번호 7자 이상")
	private String phoneNumber;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 권한 목록을 리턴(유저권한) - 중요
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public boolean isAccountNonExpired() {
		// 계정이 만료 되지 않았는지?
		return true; // 만료안됨
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정이 잠겨 있지 않았는지?
		return true; // 잠기지 않음
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 비밀번호가 만료되지 않았는지?
		return true; // 만료안됨
	}

	@Override
	public boolean isEnabled() {
		// 사용가능한 계정?
		return true;
	}
	
}
