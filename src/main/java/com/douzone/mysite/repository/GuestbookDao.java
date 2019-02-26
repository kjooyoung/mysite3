package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	@Autowired
	private SqlSession sqlSession;
	
	public long insert(GuestbookVo vo) {
		sqlSession.insert("guestbook.insert", vo);
		return vo.getNo();
	}
	
	public int delete(GuestbookVo vo) {
		return sqlSession.delete("guestbook.delete", vo);
	}
	
	public List<GuestbookVo> getList(){
		return sqlSession.selectList("guestbook.getList");
	}
	
	public List<GuestbookVo> getListByPage(int page){
		return sqlSession.selectList("guestbook.getList", page);
	}

}
