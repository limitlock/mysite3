package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cafe24.mysite.repository.BoardDao;
import com.cafe24.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	public void list(Model model, Long page) {
		page = (page - 1) * 5;
		List<BoardVo> list = boardDao.getList(page);
		model.addAttribute("list", list);

		Long maxNo = boardDao.get();
		if(maxNo == 0) {
			maxNo = 1L;
		}
		model.addAttribute("maxNo", maxNo);

	}

	public void write(BoardVo vo) {
		boardDao.insert(vo);

	}

	public void view(Model model, Long boardNo) {
		List<BoardVo> list = boardDao.viewGetList(boardNo);
		model.addAttribute("list", list);
	}

	public void hit(BoardVo vo) {
		boardDao.hitUpdate(vo);
	}

	public void delete(BoardVo vo) {
		boardDao.delete(vo);

	}

	public void modify(BoardVo vo) {
		boardDao.update(vo);

	}

	public void view_modify(Model model, Long no) {
		List<BoardVo> list = boardDao.viewGetList(no);
		model.addAttribute("list", list);
	}

	public void search(Model model, String inputTitle, Long page) {
		page = (page - 1) * 5;
		inputTitle = "%" + inputTitle + "%";
		List<BoardVo> list = boardDao.search(inputTitle, page);
		Long maxNo = boardDao.searchGet(inputTitle);
		model.addAttribute("maxNo", maxNo);
		model.addAttribute("list", list);
	}

	public void reply(BoardVo vo) {
		System.out.println("service : "+vo);
		boardDao.replyUpdate(vo);
		
		boardDao.replyInsert(vo);

	}
	

}
