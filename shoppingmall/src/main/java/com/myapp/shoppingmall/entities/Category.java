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
@Table(name = "categories")
@Data
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "카테고리를 입력해주세요.")
	@Size(min = 2, message = "카테고리는 2자 이상")
	private String name;
	private String slug; // title을 소문자 띄어쓰기 특수문자등을 하이픈으(-)로 바꿈
	
	private int sorting;
}
