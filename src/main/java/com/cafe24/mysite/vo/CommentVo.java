package com.cafe24.mysite.vo;

public class CommentVo {
	private Long no;
	private String name;
	private String password;
	private String content;
	private String curDate;
	private Long boardNo;
	private Long index;

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCurDate() {
		return curDate;
	}

	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public Long getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(Long boardNo) {
		this.boardNo = boardNo;
	}

}
