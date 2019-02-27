package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GalleryVo;

@Repository
public class GalleryDao {
	@Autowired
	private SqlSession sqlSession;
	
	public List<GalleryVo> getList(){
		return sqlSession.selectList("gallery.getList");
	}

	public int delete(long no) {
		return sqlSession.delete("gallery.delete", no);
	}
	
	public int insert(GalleryVo vo) {
		return sqlSession.insert("gallery.insert", vo);
	}
	
	public String getUrlByNo(long no) {
		return sqlSession.selectOne("gallery.getUrlByNo", no);
	}
}
