package com.myapp.bbs.model;

/**
 * 페이지 계산을 위한 클래스
 * @author admin
 *
 */
public class Criteria {
	
	/* 현재 페이지 */
	private int pageNum;
	
	/* 한 페이지 당 보여질 게시물 갯수 */
	private int amount;
	
	/* 스킵 할 게시물 수( (pageNum-1) * amount ) */
	private int skip;
	
	/* 💥 검색어 키워드 💥 */   
	private String keyword;	
	
	/* 검색 타입(뷰에서 선택됨) */
	private String type;
	
	/* 검색 타입 배열(type을 배열로 변환) */
	private String[] typeArr;
	
	/* 기본 생성자 -> 기본 세팅 : pageNum = 1, amount = 10 */
	public Criteria() {
		this(1,10);
	}
	
	/* 생성자 => 원하는 pageNum, 원하는 amount */
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.skip = (pageNum-1)*amount;
	}

	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		// 검색할 타입만 설정되면 typeArr은 자동으로 생성
		this.typeArr = type.split("");
	}

	public String[] getTypeArr() {
		return typeArr;
	}

	public void setTypeArr(String[] typeArr) {
		this.typeArr = typeArr;
	}

	public int getPageNum() {
		return pageNum;
	}
	
	// 새로 페이지 숫자를 설정했을 때 skip도 계산해줌
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		this.skip = (pageNum-1) * amount;
	}

	// 페이지당 갯수를 바꿀경우에도  스킵을 다시 계산
	public int getAmount() {
		return amount;
	}

	//페이지당 갯수를 바꿀경우에도 스킵을 다시 계산
	public void setAmount(int amount) {
		
		this.skip= (this.pageNum-1)*amount;
		
		this.amount = amount;
	}


	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "Criteria [pageNum=" + pageNum + ", amount=" + amount + ", skip=" + skip + "]";
	}
}
