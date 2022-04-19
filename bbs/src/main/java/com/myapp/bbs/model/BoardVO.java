package com.myapp.bbs.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardVO {
	/* 게시판 번호 */
	private int bno;
	
	/* 게시판 제목 */
	private String title;
	
	/* 게시판 내용 */
	private String content;
	
	/* 게시판 작가 */
	private String writer;
	
	/* 등록 날짜 */
	private LocalDateTime regdate;
	
	/* 수정 날짜 */
	private LocalDateTime updateDate;
	
	public BoardVO() {
		
	}
	
	public BoardVO(int bno, String title, String content, String writer, LocalDateTime regdate,
			LocalDateTime updateDate) {
		super();
		this.bno = bno;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.regdate = regdate;
		this.updateDate = updateDate;
	}
	

	@Override
	public String toString() {
		return "BoardVO [bno=" + bno + ", title=" + title + ", content=" + content + ", writer=" + writer + ", regdate="
				+ regdate + ", updateDate=" + updateDate + "]";
	}	

}
