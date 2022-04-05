package com.myapp.shoppingmall.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name = "pages")
@Data // get,set,toString 자동 생성됨 - lombok
public class Page {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "제목을 입력해주세요.")
	@Size(min = 2, message = "제목은 2자 이상")
	private String title;
	private String slug; // title을 소문자 띄어쓰기 특수문자등을 하이픈으(-)로 바꿈
	
	@NotBlank(message = "내용을 입력해주세요.")
	@Size(min = 2, message = "내용은 5자 이상")
	private String content;
	private int sorting; // 정렬 순서
}
