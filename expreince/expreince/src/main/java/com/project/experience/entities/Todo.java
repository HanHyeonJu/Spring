package com.project.experience.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;


@Entity
@Table(name = "todos")
@Data // get,set,toString 자동 생성됨 - lombok
public class Todo {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		
		@NotBlank(message="제목을 입력해주세요")
		@Size(min = 2, message = "제목은 2자 이상 입력해주세요")
		private String title;
		
		private String stage; 
		
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@Column(name = "start")
		private String startDate;
		
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@Column(name = "target")
		private String targetDate;
		
		private String description;
}
