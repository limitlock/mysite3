package com.cafe24.mysite.vo;

public class BoardVo {
	private Long maxNo;
	private Long no;
	private String title;
	private String content;
	private Long groupNo;
	private Long orderNo;
	private Long depth;
	private String curDate;
	private Long hit;
	private String user;
	private Long userNo;
	private String password;

	public Long getMaxNo() {
		return maxNo;
	}

	public void setMaxNo(Long maxNo) {
		this.maxNo = maxNo;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(Long groupNo) {
		this.groupNo = groupNo;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Long getDepth() {
		return depth;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
	}

	public String getCurDate() {
		return curDate;
	}

	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}

	public Long getHit() {
		return hit;
	}

	public void setHit(Long hit) {
		this.hit = hit;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "BoardVo [maxNo=" + maxNo + ", no=" + no + ", title=" + title + ", content=" + content + ", groupNo="
				+ groupNo + ", orderNo=" + orderNo + ", depth=" + depth + ", curDate=" + curDate + ", hit=" + hit
				+ ", user=" + user + ", userNo=" + userNo + ", password=" + password + "]";
	}

}
