package com.myapp.shoppingmall.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myapp.shoppingmall.dao.CategoryRepository;
import com.myapp.shoppingmall.dao.ProductRepository;
import com.myapp.shoppingmall.entities.Category;
import com.myapp.shoppingmall.entities.Product;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private ProductRepository productRepo;

	/**
	 * 입력된 slug 카테고리별로 제품리스트 표시(페이징 포함)
	 * @param slug 카테고리 slug
	 * @param page // 표시할 페이지 번호
	 * @return products 페이지
	 */
	@GetMapping("/{slug}")
		public String category(@PathVariable String slug, Model model, @RequestParam(value="page", defaultValue="0") int page) {
		
		int perPage = 6; // 한 페이지에 최대 6개 출력
		Pageable pageable = PageRequest.of(page, perPage); // 도메인 인터페이스 해야함(Pageable), (표시할페이지, 현재페이지에 맞게(6))
		long count = 0; 
		
		// 카테고리 선택(all(전체), 그외 개별 카테고리)
		if(slug.equals("all")) {
			Page<Product> products = productRepo.findAll(pageable);
			count = productRepo.count(); // 전체 제품 수
			
			model.addAttribute("products", products); // 전체 제품들
		} else { // 카테고리 별 페이징
			Category category = categoryRepo.findBySlug(slug);
			if(category == null){
				return "redirect:/"; // 카테고리가 없으면 홈으로
				}
			String categoryId = Integer.toString(category.getId());
			String categoryName = category.getName();
			List<Product> products = productRepo.findAllByCategoryId(categoryId, pageable); // 카테고리 아이디를 통해 물건ㅇ늘 찾고 페이징(?)
			count = productRepo.countByCategoryId(categoryId);
			
			model.addAttribute("products", products); // 선택한 카티고리의 제품들
			model.addAttribute("categoryName", categoryName);
		}
		
		
		// Math.ceil 은 올림기능이 있음 2.1 => 3 (반올림 아님)
		double pageCount = Math.ceil((double)count/(double)perPage); // ex) 총 13개다 13/6 =2.12 int로 나누면 소수점이 남지 않아서 2페이지만 출력 그렇기 때문에 double을 이용해서 소수점을 남겨줌 => 3페이지가 출력됨
		
		model.addAttribute("pageCount", (int)pageCount); // 홈페이지(이미 올림된 숫자가 오기 때문에 int로 보내줌)
		model.addAttribute("perPage", perPage); // 페이지당 표시 아이템수
		model.addAttribute("count", count); // 총 아이템 갯수
		model.addAttribute("page", page); // 현재 페이지
		
		
		return "products"; // products.html 페이지로 이동
	}
}
