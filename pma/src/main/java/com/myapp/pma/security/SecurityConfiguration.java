package com.myapp.pma.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
// Bean을 어노테이션한 메소드를 객체화하기 위해 스프링에게 이 클래스 안에 객체화할 메소드가 있다고 알려주는 역할
@Configuration

//시큐리티 설정을 위해서 WebSecurityConfigurerAdapter 상속
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	// 인증
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 메모리에 유저, 비밀번호, roles(허용범위 유저, 관리자 등)
		auth.inMemoryAuthentication()
			.withUser("drv98").password(getPasswordEncoder().encode("1234")).roles("USER")
			.and()
			.withUser("admin").password(getPasswordEncoder().encode("pass")).roles("ADMIN");
	}
	
	@Bean // 패스워드 암호화 객체, 인증 시 password에 사용하기 위해 객체화
	public PasswordEncoder getPasswordEncoder() {
		return  NoOpPasswordEncoder.getInstance(); // 테스트용-비밀번호 암호화(x)
	}
	
	// 허가
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.antMatchers("/projects/new").hasRole("ADMIN") // 새 프로젝트는 관리자
			.antMatchers("/projects/save").hasRole("ADMIN") 
			.antMatchers("/employees/new").hasRole("ADMIN") // 새 직원은 관리자
			.antMatchers("/employees/save").hasRole("ADMIN") 
			.antMatchers("/employees").authenticated() // 인증된 사용자
			.antMatchers("/projects").authenticated()
			.antMatchers("/","/**").permitAll() //기본페이지는 아무나
			.and()
			.formLogin(); //로그인창 사용
		
		// 시큐리티에서는 기본적으로 csrf 방지가 적용 중
		// http.csrf().disable();
	}
}
