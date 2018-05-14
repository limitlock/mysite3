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

	public boolean delete(GuestbookVo vo) {
		int count = guestbookDao.delete(vo);
		return count == 1;
	}

	public List<GuestbookVo> getMessageList(Long no) {
		List<GuestbookVo> list = guestbookDao.getList2(no);
		return list;
	}

	
	public GuestbookVo insertMessage2(GuestbookVo guestbookVo) {
		GuestbookVo vo = null;
		int count = guestbookDao.insert(guestbookVo);
		if(count == 1) {
			return guestbookDao.get(guestbookVo.getNo());
			
			
		}
		return vo;
	}
	
}
