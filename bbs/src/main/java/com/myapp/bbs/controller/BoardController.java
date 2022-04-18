package com.myapp.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myapp.bbs.model.BoardVO;
import com.myapp.bbs.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}

	@GetMapping("/list")
	public String boardListGet(Model model) {
		model.addAttribute("boardList", boardService.getList());
		return "list";
	}
	
	@GetMapping("/enroll")
	public String boardEnrollGet(Model model) {
		model.addAttribute("board", new BoardVO());
		return "enroll";
	}
	
	@PostMapping("/enroll")
	public String boardEnrollPost(BoardVO board, RedirectAttributes attr) {
//		log.info("BoardVO : " + board);
		boardService.enroll(board);
		attr.addFlashAttribute("message", "게시글 등록 성공");
		return"redirect:/board/list";
	}
	
	/**
	 * 게시판 글을 조회하기
	 * @param bno
	 * @param model
	 * @return
	 * 
	 */
	@GetMapping("/get")
	public String boardPageGET(@RequestParam("bno") int bno, Model model) {
		model.addAttribute("board", boardService.getPage(bno));
		return "get";
	}
	
	@GetMapping("/modify")
	public String boardModifyGET(@RequestParam("bno") int bno, Model model) {
		model.addAttribute("board", boardService.getPage(bno));
		return "modify";
	}
	
	@PostMapping("/modify")
	public String boardModifyPost(BoardVO board, RedirectAttributes attr) {
		boardService.modify(board); // modify 이미지에서 수정된 내용을 업데이트함
		attr.addFlashAttribute("message", "수정성공!");
		// post - redirect - get
		return"redirect:/board/list"; // redirect를 해주지 않으면 새로고침 했을 때 중복으로 post될 수 있음 그래서 아예 새로운 요청으로 보내버림
	}
	
	@GetMapping("/delete")
	public String boardDeleteGet(@RequestParam("bno") int bno,  RedirectAttributes attr) {
		boardService.delete(bno);
		attr.addFlashAttribute("message", "삭제성공!");
		return "redirect:/board/list";
	}
}
