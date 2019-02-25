package com.douzone.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.SiteVo;

@Repository
public class SiteDao {
	@Autowired
	private SqlSession sqlSession;
	
	public SiteVo get() {
		return sqlSession.selectOne("site.get");
	}
	
	public int update(SiteVo vo) {
		return sqlSession.update("site.update", vo);
	}
}
