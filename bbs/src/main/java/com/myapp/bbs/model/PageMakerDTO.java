package com.myapp.bbs.model;

/**
 * 페이지네이션을 위해서 전체게시물수와 cri를 입력받아 계산하여
 * 시작페이지, 끝페이지, 이전,다음페이지 유무를 저장
 * @author admin
 *
 */
public class PageMakerDTO {
	
	/*시작 페이지*/
	private int startPage;
	
	/*끝 페이지*/
	private int endPage;
	
	/*이전 페이지, 다음 페이지*/
	private boolean prev, next;
	
	/*전체 게시물 수*/
	private int total;
	
	/*현재 페이지, 페이지당 게시물 표시수 정보*/
	private Criteria cri;

	public PageMakerDTO(Criteria cri, int total) {
		
		this.total = total;
		this.cri = cri;
		
		/*마지막 페이지 : 10단위로 표시 현재페이지가 1~10일 경우에 마지막은 10이다(?), 11~20일 때는 20, 21~30일 때는 30 (ceil은 올림) ??? */
		this.endPage = (int)(Math.ceil(cri.getPageNum()/10.0)) * 10; //(=pageNum)
		
		/*시작 페이지*/
		this.startPage = this.endPage - 9;
		
		/* 실제 마지막 페이지 (전체게시물*1.0)/10 => 전체 게시물일 100일 때 마지막 페이지는 10*/
		int realEnd = (int)(Math.ceil(total * 1.0/cri.getAmount()));
		
		/* 마지막 페이지를 10단위로 끝나게 해두었는데 게시물이 50개밖에 없는 경우에는 amount를 10으로 설정해두어서 5페이지가 마지막이어야 함
		 * 그렇기 때문에 endPage보다 realEnd의 값이 작아짐 그것을 방지하기 위함*/
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		/* 이전페이지의 참 : 시작페이지의 값이 1보다 큰 경우는 참*/
		this.prev = this.startPage > 1;
		
		/* 다음 페이지의 참 : realEnd페이지가 end페이지보다 값이 큰 경우는 참*/
		this.next = this.endPage < realEnd;
		
		
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	@Override
	public String toString() {
		return "PageMakerDTO [startPage=" + startPage + ", endPage=" + endPage + ", prev=" + prev + ", next=" + next
				+ ", total=" + total + ", cri=" + cri + "]";
	}

}
