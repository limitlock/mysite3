package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cafe24.mysite.repository.CommentDao;
import com.cafe24.mysite.vo.CommentVo;

@Service
public class CommentService {
	@Autowired
	private CommentDao commentDao;

	public void commentList(Model model, Long boardNo) {
		List<CommentVo> list = commentDao.GetList(boardNo);
		model.addAttribute("commentlist", list);
		model.addAttribute("boardNo",boardNo);
	}

	public void write(CommentVo vo) {
		commentDao.insert(vo);

	}
	
	public void delete(CommentVo vo) {
		commentDao.delete(vo);
	}

}
