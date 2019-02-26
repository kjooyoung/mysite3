package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookDao;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestbookDao guestbookDao;
	
	public List<GuestbookVo> getList(){
		List<GuestbookVo> list = guestbookDao.getList();
		return list;
	}
	
	public List<GuestbookVo> getListByPage(int page){
		List<GuestbookVo> list = guestbookDao.getListByPage(page);
		return list;
	}
	
	public long add(GuestbookVo vo) {
		return guestbookDao.insert(vo);
	}
	
	public long delete(GuestbookVo vo) {
		if(guestbookDao.delete(vo) == 0) {
			
			return 0;
		}
		return vo.getNo();
	}
}
