package com.myapp.pma.dto;

public interface EmployeeProject {
	
	// 스프링이 자동 생성할 수 있도록 get메소드만 적어줌
	// DB에서 쿼리 결과를 가져옴, 가져오기만 하는거라 set할 필요 없음
	public String getLastName();
	public String getFirstName();
	public String getCount();
}
