package com.myapp.bbs.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;

import com.myapp.bbs.model.Criteria;

import com.myapp.bbs.model.BoardVO;

//import lombok.extern.java.Log;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 DB로 테스트
@Rollback(value = false) // 테스트할 때 롤백 처리 하지 않음
//@Log
public class BoardMapperTest {
	
	// JUNIT 5버전 테스트
	@Autowired
	private BoardMapper boardMapper;
	
	// 테스트 한 것들은 주석처리함
	@Test
	public void test() {
		/* 게시판 등록 */
//		BoardVO vo = new BoardVO();
//		
//		vo.setTitle("제목테스트");
//		vo.setContent("내용테스트");
//		vo.setWriter("글쓴이");
//		
//		boardMapper.enroll(vo);
		
		/* 게시판 조회 */
//		List<BoardVO> list = boardMapper.getList();
//		// 반복문을 써서 테스트
//		for(int i=0; i < list.size(); i++) {
//			log.info("" + list.get(i));
//		}
		
		/* 향상된 foreach문*/
		/*for(BoardVO vo : list) {
			log.info("" + vo);
		}*/
		
		/* foreach 메소드와 람다식 */
		/*list.forEach(board -> log.info("" + board));*/
		
		/* 게시판 조회 */
//		int bno = 1;
//		log.info(""+boardMapper.getPage(bno));
		
		/* 게시판 수정 */
//		BoardVO board = new BoardVO();
//		board.setBno(1);
//		board.setTitle("수정된 제목");
//		board.setContent("수정된 게시글 내용");
//		
//		int result = boardMapper.modify(board);
//	
//		log.info("결과: " + result);
		
		/* 게시판 삭제 */
//		int result = boardMapper.delete(5);
//		log.info("결과: " + result);
		
		/* 게시판 페이징 */
		Criteria cri = new Criteria(); // 기본 생성자 생성(1, 10)(pageNum, amount)
		
		cri.setPageNum(2); // 2번재 페이지부터라고 설정(181~190) - amount를 설정해주지 않았을 때
		cri.setAmount(5); // 1번째 페이지가 (196~200)이 되고 2번째 페이지가 (191~195)가 되면서 2번재 페이지 출력
		
	    List<BoardVO> list = boardMapper.getListPaging(cri);
	     
	    list.forEach(board -> System.out.println(board.toString()));
	}
	
}
