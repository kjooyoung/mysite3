package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.ReplyDao;
import com.douzone.mysite.vo.ReplyVo;

@Service
public class ReplyService {
	@Autowired
	private ReplyDao replyDao;
	
	public void write(ReplyVo vo) {
		replyDao.insert(vo);
	}
	
	public void delete(Long no) {
		replyDao.delete(no);
	}
	
	public void update(ReplyVo replyVo) {
		replyDao.update(replyVo);
	}
}
