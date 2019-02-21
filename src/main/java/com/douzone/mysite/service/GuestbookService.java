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
	
	public void add(GuestbookVo vo) {
		guestbookDao.insert(vo);
	}
	
	public int delete(GuestbookVo vo) {
		return guestbookDao.delete(vo);
	}
}
