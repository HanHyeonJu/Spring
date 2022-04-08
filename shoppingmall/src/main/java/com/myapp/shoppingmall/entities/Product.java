package com.myapp.shoppingmall.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "품명을 입력해주세요.")
	@Size(min = 2, message = "품명은 2자 이상")
	private String name;
	
	private String slug;
	
	@NotBlank(message = "설명을 입력해주세요.")
	@Size(min = 5, message = "설명은 5자 이상")
	private String description;
	
	private String image;
	
	// 1~999999999 까지 작성가능
	@Pattern(regexp="^[1-9][0-9]*") // 시작하는 첫 문자는 1-9만 입력가능(^) (*) 여러번 입력가능 ex) 0100(에러남) 100(정상)
	private String price; // 문자열로 하고 변환해서 사용
	
	@Pattern(regexp="^[1-9][0-9]*", message="카테고리를 선택해주세요")
	@Column(name="category_id")
	private String categoryId;
	
	// 쿼리에서 알아서 등록, 수정된 시간에 저장됨
	@Column(name = "created_at" , updatable = false) // 한 번만 저장되도록 설정해줌
	@CreationTimestamp  // 생성(insert)시 자동
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	@UpdateTimestamp // 수정하면 자동으로 시수정된 시간이 저장됨(?)
	private LocalDateTime updateAt;
}
