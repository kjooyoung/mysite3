package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.SiteDao;
import com.douzone.mysite.vo.SiteVo;

@Service
public class SiteService {
	@Autowired
	private SiteDao siteDao;
	
	public SiteVo get() {
		return siteDao.get();
	}
	
	public void modify(SiteVo vo) {
		siteDao.update(vo);
	}
	
}
