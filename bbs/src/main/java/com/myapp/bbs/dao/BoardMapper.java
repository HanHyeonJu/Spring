package com.myapp.bbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.myapp.bbs.model.BoardVO;
import com.myapp.bbs.model.Criteria;

@Mapper
public interface BoardMapper {
	
	/* 게시판 등록 */
	public void enroll(BoardVO board);
	
	/* 게시판 목록(페이징 적용) : pageNum, amount를 입력받아 객체 cri 생성 객체의 값이 없으면 기본(1,10) */
	public List<BoardVO> getListPaging(Criteria cri);
	
	/* 게시판 조회 */
	public BoardVO getPage(int bno);
	
	/* 게시판 수정 */
	public int modify(BoardVO board);
	
	/* 게시판 삭제 */
	public int delete(int bno);
	
	/* 게시물 총 갯수 */
	 public int getTotal(Criteria cri);
}
