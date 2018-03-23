package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;
	
	public Long searchGet(String inputTitle) { // 들어갈 값이 많다면 vo 를 넣어서 사용한다.

		return sqlSession.selectOne("board.search_get", inputTitle);

	}

	public List<BoardVo> search(String inputTitle, Long page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inputTitle", inputTitle);
		map.put("page", page);

		return sqlSession.selectList("board.search", map);
	}

	public boolean hitUpdate(BoardVo vo) {
		int count = sqlSession.update("board.hit", vo);
		return count == 1;
	}

	public List<BoardVo> viewGetList(Long boardNo) {

		return sqlSession.selectList("board.view", boardNo);
	}

	public boolean update(BoardVo vo) {
		int count = sqlSession.update("board.modify", vo);

		return count == 1;
	}

	public int delete(BoardVo vo) {
		int count = sqlSession.delete("board.delete", vo);
		return count;
	}

	public int insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
		return count;
	}

	public boolean replyUpdate(BoardVo vo) {
		System.out.println("dao update: "+vo);
		int count = sqlSession.update("board.reply_update", vo);
		return count == 1;
	}

	public boolean replyInsert(BoardVo vo) {
		System.out.println("dao insert: "+vo);
		int count = sqlSession.insert("board.reply_insert", vo);

		return count == 1;
	}

	public Long get() {
		return sqlSession.selectOne("board.get");
	}

	// public List<BoardVo> getList(int page)
	public List<BoardVo> getList(Long page) {
		return sqlSession.selectList("board.getList", page);
	}

}
