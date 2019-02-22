package com.douzone.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> getList(Map<String, Object> map){
		return sqlSession.selectList("board.getList", map);
	}
			
	public BoardVo getBoard(long no){
		return sqlSession.selectOne("board.getBoard", no);
	}
	
	public long insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
	}
	
	public int updateOrder(long no) {
		return sqlSession.update("board.updateOrder", no);
	}
	
	public int update(BoardVo vo) {
		return sqlSession.update("board.update", vo);
	}
	
	public int updateHit(long no) {
		return sqlSession.update("board.updateHit", no);
	}
	
	public int delete(long no) {
		return sqlSession.delete("board.delete", no);
	}
	
	public int getTotalCount(String kwd) {
		return sqlSession.selectOne("board.getTotalCount", kwd);
	}
	
}
