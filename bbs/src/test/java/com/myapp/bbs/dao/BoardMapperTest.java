package com.myapp.bbs.dao;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;

import com.myapp.bbs.model.BoardVO;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 DB로 테스트
@Rollback(value = false) // 테스트할 때 롤백 처리 하지 않음ㄴ
public class BoardMapperTest {
	
	// JUNIT 5버전 테스트
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void test() {
		BoardVO vo = new BoardVO();
		
		vo.setTitle("제목테스트");
		vo.setContent("내용테스트");
		vo.setWriter("글쓴이");
		
		boardMapper.enroll(vo);
	}
}
