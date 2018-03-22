package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cafe24.mysite.repository.GuestbookDao;
import com.cafe24.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {

	@Autowired
	private GuestbookDao guestbookDao;

	public void list(Model model) {

		List<GuestbookVo> list = guestbookDao.getList();
		model.addAttribute("list", list);

	}

	public void add(GuestbookVo vo) {
		guestbookDao.insert(vo);

	}

	public void delete(GuestbookVo vo) {
		guestbookDao.delete(vo);

	}

}
