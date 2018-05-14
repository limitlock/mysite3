package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {

	@Autowired
	private SqlSession sqlSession;

	public int delete(GuestbookVo vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", vo.getNo());
		map.put("password", vo.getPassword());

		int count = sqlSession.delete("guestbook.delete", vo);

		return count;
	}

	public int insert(GuestbookVo vo) {
		int count = sqlSession.insert("guestbook.insert", vo);
		return count;
	}

	public List<GuestbookVo> getList() {
		return sqlSession.selectList("guestbook.getList");
	}

	public List<GuestbookVo> getList2(Long no) {
		return sqlSession.selectList("guestbook.getList2",no);
	}

	public GuestbookVo get(Long no) {
		return sqlSession.selectOne("guestbook.getByNo", no);
	}

}
