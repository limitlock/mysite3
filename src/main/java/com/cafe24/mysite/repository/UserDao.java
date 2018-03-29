package com.cafe24.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.exception.UserDaoException;
import com.cafe24.mysite.vo.UserVo;

@Repository
public class UserDao {
	// Dao 안에는 비즈니스 용어를 쓰면 안된다. (나중에 혼동이 올 수 있음 => 비즈니스 용어는 서비스 영역에서!)

	@Autowired
	private SqlSession sqlSession;

	public boolean update(UserVo vo) {
		int count = sqlSession.update("user.update", vo);
		return count == 1;
	}

	public UserVo get(Long no) {
		return sqlSession.selectOne("user.getByNo", no);

	}
	
	public UserVo get(String email) {
		return sqlSession.selectOne("user.getByEmail", email);

	}


	public UserVo get(UserVo vo) throws UserDaoException { // 들어갈 값이 많다면 vo 를 넣어서 사용한다.
		
		return sqlSession.selectOne("user.getByEmailAndPassword", vo);
		
	}

	public boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);

		return count == 1;
	}

}
