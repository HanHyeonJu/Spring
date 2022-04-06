package com.myapp.shoppingmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.shoppingmall.entities.Page;

public interface PageRepository extends JpaRepository<Page, Integer> {
	// CrudRepository와 달리 JpaRepository는 이미 list타입으로 findAll하는 메소드가 존재하기 때문에 안해줘도 됨
	
	
	Page findBySlug(String slug);

	Page findBySlugAndIdNot(String slug, int id);

	List<Page> findAllByOrderBySortingAsc();

}
