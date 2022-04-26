package com.myapp.bbs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.myapp.bbs.interceptor.LoginCheckInterceptor;

/* Interceptor 스프링에 등록시키고 적용시키기 위해 생성*/
@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 인터셉터를 추가하는 메소드
		registry
			.addInterceptor(new LoginCheckInterceptor())
			.addPathPatterns("/board/**") // 인터셉터 적용할 주소
		    .excludePathPatterns("/board/list", "/board/get"); // 인터셉터 적용 안할 주소
	}

}
