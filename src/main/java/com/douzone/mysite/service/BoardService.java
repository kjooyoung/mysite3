package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.repository.ReplyDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.Criteria;
import com.douzone.mysite.vo.PageMaker;
import com.douzone.mysite.vo.ReplyVo;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private ReplyDao replyDao;
	
	public Map<String, Object> list(Integer page, String kwd){
		//pager 알고리즘
		Criteria cri = new Criteria();
		cri.setPage(page);
		List<BoardVo> list = boardDao.getList(kwd, cri.getPageStart(), cri.getPerPageNum());
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		
		int totalNum = new BoardDao().getTotalCount(kwd);
		pageMaker.setTotalCount(totalNum);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("page", page);
		map.put("pageMaker", pageMaker);
		map.put("totalNum", totalNum);
		map.put("kwd", kwd);
		
		return map;
	}
	
	public Map<String, Object> view(Long no) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		BoardVo boardVo = boardDao.getBoard(no);
		List<ReplyVo> replyList = replyDao.getList(no);
		boardDao.updateHit(no);
		map.put("board", boardVo);
		map.put("reply", replyList);
		return map;
	}
	
	public void write(Long no,BoardVo boardVo) {
		if(no == 0) {
			boardDao.insert(boardVo);
		}else {
			boardDao.updateOrder(no);
			boardDao.insert(no, boardVo);
		}
		
	}
	
	public void delete(Long no) {
		boardDao.delete(no);
	}
	
	public BoardVo getBoard(Long no) {
		return boardDao.getBoard(no);
	}
	
	public void update(BoardVo vo) {
		boardDao.update(vo);
	}
}
