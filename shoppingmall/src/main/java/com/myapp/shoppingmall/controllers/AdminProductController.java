package com.myapp.shoppingmall.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myapp.shoppingmall.dao.CategoryRepository;
import com.myapp.shoppingmall.dao.ProductRepository;
import com.myapp.shoppingmall.entities.Category;
import com.myapp.shoppingmall.entities.Product;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@GetMapping
	public String index(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		
		int perPage = 6; // 한 페이지에 최대 6개 출력
		Pageable pageable = PageRequest.of(page, perPage); // 도메인 인터페이스 해야함(Pageable), (표시할페이지, 현재페이지에 맞게(6))
		
		
		Page<Product> products = productRepo.findAll(pageable); // List Page로 변경 (마찬가지로 도메인 페이지 import)
		
		List<Category> categories = categoryRepo.findAll();
		
		HashMap<Integer, String> cateIdAndName = new HashMap<>();
		for(Category category : categories) {
			cateIdAndName.put(category.getId(), category.getName());
		}
		
		model.addAttribute("products", products);
		model.addAttribute("cateIdAndName", cateIdAndName);
		
		// 페이지를 보여주기 위해(페이지네이션) 계산
		long count = productRepo.count(); // 전체갯수 구함(Long타입 리턴)
		// Math.ceil 은 올림기능이 있음 2.1 => 3 (반올림 아님)
		double pageCount = Math.ceil((double)count/(double)perPage); // ex) 총 13개다 13/6 =2.12 int로 나누면 소수점이 남지 않아서 2페이지만 출력 그렇기 때문에 double을 이용해서 소수점을 남겨줌 => 3페이지가 출력됨
		
		model.addAttribute("pageCount", (int)pageCount); // 홈페이지(이미 올림된 숫자가 오기 때문에 int로 보내줌)
		model.addAttribute("perPage", perPage); // 페이지당 표시 아이템수
		model.addAttribute("count", count); // 총 아이템 갯수
		model.addAttribute("page", page); // 현재 페이지
		
		
		return "admin/products/index";
	}
	
	@GetMapping("/add")
	public String add(@ModelAttribute Product product, Model model) {
		List<Category> categories = categoryRepo.findAll();
		
		model.addAttribute("categories", categories); 
		
		// 상품을 추가하는 add페이지에 제품객체와 제품의 카테고리를 선택할 수 있음(카테고리 리스트를 전달함)
		return "admin/products/add";
	} // product는 그냥 빈 객체이기 때문에 매개변수를 통해 @ModelAttribute가 가능하지만 categories는 DB에서 찾아와야하기 때문에 안됨
	
	// 프로덕트에는 파일의 주소의 이름은 저장되고 파일은 따로 저장소를 생성하여서 그곳에 저장됨
	@PostMapping("/add")
	public String add(@Valid Product product, BindingResult bindingResult, 
					  MultipartFile file, RedirectAttributes attr, Model model) throws IOException  {
		
		if(bindingResult.hasErrors()) {
			List<Category> categories = categoryRepo.findAll();
			model.addAttribute("categories", categories); 
			return "admin/products/add"; // 유효성 검사 에러시 다시 되돌아감
		}
		
		boolean fileOk = false;
		byte[] bytes = file.getBytes(); // 업로드된 이미지 파일의 데이터
		String fileName = file.getOriginalFilename(); // 파일의 이름
		// 주소는 shoppingmall프로젝트 안에서의 기준
		Path path = Paths.get("src/main/resources/static/media/"+ fileName); //저장할 파일의 위치와 이름까지
		
		if(fileName.endsWith("jpg") || fileName.endsWith("png")) {
			fileOk = true; // 확장자가 .jpg 또는 .png인 경우에만 OK
		}
		
		// 성공적으로 추가됨
		attr.addFlashAttribute("message","상품이 성공적으로 추가됨");
		attr.addFlashAttribute("alertClass","alert-success");
		
		// 슬러그 만들기
		String slug = product.getName().toLowerCase().replace(" ", "-");
		// 똑같은 상품명이 있는지 검사
		Product productExist = productRepo.findByName(product.getName());
		
		if(!fileOk) {
			attr.addFlashAttribute("message","이미지는 jpg나 png만 사용해주세요");
			attr.addFlashAttribute("alertClass","alert-danger");
			attr.addFlashAttribute("product", product); // 에러가 나더라도 적은 거 그대로 보일 수 있게함
		}
		else if(productExist != null) {
			attr.addFlashAttribute("message","이미 등록된 상품입니다");
			attr.addFlashAttribute("alertClass","alert-danger");
			attr.addFlashAttribute("product", product);
		}
		else {
			// 상품과 이미지 파일 저장
			product.setSlug(slug);
			product.setImage(fileName); // 이미지는 파일의 이름만 입력(주소는 /media/ 폴더이므로 동일)
			productRepo.save(product); // 제품을 저장
			
			Files.write(path, bytes); // (저장주소, 데이터)
		}
		
		return "redirect:/admin/products/add";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		
		Product product = productRepo.getById(id);
		List<Category> categories = categoryRepo.findAll();
		
		model.addAttribute("categories", categories);
		model.addAttribute("product", product);
		return "admin/products/edit";
	}
	
	@PostMapping("/edit")
	public String edit(@Valid Product product, BindingResult bindingResult, 
					  MultipartFile file, RedirectAttributes attr, Model model) throws IOException  {
		// 미리 id로 수정 전의 제품을 불러옴
		Product currentProduct = productRepo.getById(product.getId());
		
		if(bindingResult.hasErrors()) {
			List<Category> categories = categoryRepo.findAll();
			model.addAttribute("categories", categories); 
			return "admin/products/edit"; // 유효성 검사 에러시 다시 되돌아감
		}
		
		boolean fileOk = false;
		byte[] bytes = file.getBytes(); // 업로드된 이미지 파일의 데이터
		String fileName = file.getOriginalFilename(); // 파일의 이름
		// 주소는 shoppingmall프로젝트 안에서의 기준
		Path path = Paths.get("src/main/resources/static/media/"+ fileName); //저장할 파일의 위치와 이름까지
		
		if(!file.isEmpty()) { // 새 이미지 파일이 있으면
			if(fileName.endsWith("jpg") || fileName.endsWith("png")) {
				fileOk = true; // 확장자가 .jpg 또는 .png인 경우에만 OK
			}
		} else {
			fileOk = true; // 이미지를 수정 하지 않을 경우 기존 이미지 사용
		}
		
		
		// 성공적으로 추가됨
		attr.addFlashAttribute("message","상품이 성공적으로 수정됨");
		attr.addFlashAttribute("alertClass","alert-success");
		
		// 슬러그 만들기
		String slug = product.getName().toLowerCase().replace(" ", "-");
		// 똑같은 상품명이 있는지 검사
		// ????
		Product productExist = productRepo.findBySlugAndIdNot(slug, product.getId());
		
		if(!fileOk) {
			attr.addFlashAttribute("message","이미지는 jpg나 png만 사용해주세요");
			attr.addFlashAttribute("alertClass","alert-danger");
			attr.addFlashAttribute("product", product); // 에러가 나더라도 적은 거 그대로 보일 수 있게함
		}
		else if(productExist != null) {
			attr.addFlashAttribute("message","이미 등록된 상품입니다");
			attr.addFlashAttribute("alertClass","alert-danger");
			attr.addFlashAttribute("product", product);
		}
		else {
			// 상품과 이미지 파일 저장
			product.setSlug(slug);
			
			if (!file.isEmpty()) { // 수정할 이미지 파일이 있을 경우에만 저장(이때 이전 파일 삭제)
				Path currentPath = Paths.get("src/main/resources/static/media/" + currentProduct.getImage());
				Files.delete(currentPath);
				product.setImage(fileName);				
				Files.write(path, bytes);	//Files 클래스를 사용해 파일을 저장		
			} else {
				product.setImage(currentProduct.getImage());	
			}
			
			productRepo.save(product);
		}
		
		return "redirect:/admin/products/edit/" + product.getId();
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) throws IOException {
		// id로 상품을 삭제하기 전에 먼저 id로 제품객체를 불러와서 이미지 파일을 삭제한후 제품 삭제	
		Product currentProduct = productRepo.getById(id);
		Path currentPath = Paths.get("src/main/resources/static/media/" + currentProduct.getImage());
		Files.delete(currentPath);
		productRepo.deleteById(id);
		
		redirectAttributes.addFlashAttribute("message", "성공적으로 삭제 되었습니다.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");		
		
		return "redirect:/admin/products";
	}

}
