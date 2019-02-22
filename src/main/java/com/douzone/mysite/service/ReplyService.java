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
		System.out.println(vo.getNo());
		if(vo.getNo() != 0) {
			// 대댓글
			ReplyVo momReply = replyDao.getReply(vo.getNo());
			long orderNo = replyDao.getRefOrderNo(momReply);
			if(orderNo == 0) {
				orderNo = replyDao.getMaxOrderNo(momReply);
				System.out.println("맨밑으로"+orderNo);
				vo.setOrderNo(orderNo);
			}else {
				momReply.setOrderNo(orderNo);
				replyDao.updateOrder(momReply);
			}
			
			vo.setGroupNo(momReply.getGroupNo());
			vo.setDepth(momReply.getDepth()+1);
			replyDao.insertReReply(vo);
		} else {
			//그냥 댓글
			replyDao.insert(vo);
		}
	}
	
	public void delete(Long no) {
		replyDao.delete(no);
	}
	
	public void update(ReplyVo replyVo) {
		replyDao.update(replyVo);
	}
}
