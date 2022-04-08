package com.myapp.shoppingmall;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	// 기본페이지 설정
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		// 컨트롤러 대신 뷰를 매핑 가능
//		registry.addViewController("/").setViewName("home");
//	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 저장된 이미자 파일의 경로를 지정함(이미지를 사용하기 위함)
		// 사진 영문으로 저장해둬야 index화면에 표시됨
		registry
			.addResourceHandler("/media/**")
			.addResourceLocations("file:///C:/Spring/spring_workSpace/shoppingmall/src/main/resources/static/media/");
	}
	
}