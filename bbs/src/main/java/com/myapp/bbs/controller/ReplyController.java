package com.myapp.bbs.controller;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.bbs.model.ReplyVO;
import com.myapp.bbs.service.ReplyService;

@RestController
@RequestMapping("/reply")
public class ReplyController {
	
	// ReplyService 객체를 생성자 주입
	private ReplyService replyService;
	
	// 생성자 객체 주입시에는 @Autowired 필요없음
	public ReplyController(ReplyService replyService) {
		this.replyService = replyService;
	}
	
	@PostMapping
	public ReplyVO replyEnrollPost(@RequestBody ReplyVO reply) {
		// 입력된 json 타입 데이터를 받아서 reply객체 리턴(제이슨타입)
		replyService.enroll(reply);
		return reply; // json타입
	}
	
	// 이 bno는 board.bno?
	@GetMapping("/{bno}")
	public List<ReplyVO> replyListGet(@PathVariable int bno){
		return replyService.getReplyList(bno);
	}
	
	// 수정 같은 경우에는 ReplyVo 객체를 requestBody로 받고 그 받은 객체에 id가 있기 때문에 "/{id}" 안 해줘도 됨(?)
	@PutMapping
	public ReplyVO replyUpdatePut(@RequestBody ReplyVO reply) {
		replyService.modify(reply); // DB에 댓글 데이터 수정하기
		return reply;
	}
	
	@DeleteMapping("/{id}")
	public void replyDelete(@PathVariable("id") int reply_no) {
		replyService.delete(reply_no);
	}
}
