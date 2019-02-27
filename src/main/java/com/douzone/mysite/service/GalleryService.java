package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GalleryDao;
import com.douzone.mysite.repository.SiteDao;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	@Autowired
	private GalleryDao galleryDao;
	@Autowired
	private SiteDao siteDao;
	
	public void delete(long no) {
		galleryDao.delete(no);
	}
	
	public List<GalleryVo> getList(){
		return galleryDao.getList();
	}
	
	public void insert(GalleryVo vo) {
		galleryDao.insert(vo);
	}
	
	public void updateProfile(long no) {
		siteDao.updateProfile(galleryDao.getUrlByNo(no));
	}
}
