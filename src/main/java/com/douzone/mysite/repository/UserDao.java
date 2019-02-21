package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(UserVo vo){
		return sqlSession.insert("user.insert", vo);
	}
	
	public UserVo get(String email, String password) {
		//Map은 비추, vo객체에 데이터 넣고 파라미터로 넘기는게 좋음
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("email", email);
		paramMap.put("password", password);
		return sqlSession.selectOne("user.getByEmailAndPassword", paramMap);
	}
	
	public UserVo get(long no) {
		return sqlSession.selectOne("user.getByNo", no);
	}
	
	public int updateAll(UserVo userVo) {
		return sqlSession.update("user.updateAll", userVo);
	}
	
	public int update(UserVo userVo) {
		return sqlSession.update("user.update", userVo);
	}
	
	public UserVo get(String email) {
		return sqlSession.selectOne("user.getByEmail", email);
	}
}
