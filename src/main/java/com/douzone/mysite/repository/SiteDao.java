package com.douzone.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.SiteVo;

@Repository
public class SiteDao {
	@Autowired
	private SqlSession sqlSession;
	
	public SiteVo getSite() {
		return sqlSession.selectOne("site.getSite");
	}
	
	public int updateSite(SiteVo vo) {
		return sqlSession.update("site.updateSite", vo);
	}
	
	public int updateProfile(String profile) {
		return sqlSession.update("site.updateProfile", profile);
	}
}
