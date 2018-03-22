package com.cafe24.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.UserDao;
import com.cafe24.mysite.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	public void join(UserVo vo) {
		userDao.insert(vo);

	}

	public UserVo getUser(UserVo vo) {

		return userDao.get(vo.getEmail(), vo.getPassword());

	}

	public void modify(UserVo vo) {
		// TODO Auto-generated method stub
		userDao.update(vo);
	}

}
