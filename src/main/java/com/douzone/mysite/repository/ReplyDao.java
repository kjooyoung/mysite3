package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.ReplyVo;

@Repository
public class ReplyDao {
	@Autowired
	private SqlSession sqlSession;
	
	public int insertReReply(ReplyVo vo) {
		return sqlSession.insert("reply.insertReReply", vo);
	}
	public int insert(ReplyVo vo) {
		return sqlSession.insert("reply.insert", vo);
	}
	
	public int updateOrder(ReplyVo momReply) {
		return sqlSession.update("reply.updateOrder", momReply);
	}
	
	public long getRefOrderNo(ReplyVo momReply){
		return sqlSession.selectOne("reply.getRefOrderNo", momReply);
	}
	
	public long getMaxOrderNo(ReplyVo momReply){
		return sqlSession.selectOne("reply.getMaxOrderNo", momReply);
	}
	
	public ReplyVo getReply(long no){
		return sqlSession.selectOne("reply.getReply", no);
	}
	
	public List<ReplyVo> getList(long no){
		return sqlSession.selectList("reply.getList", no);
	}
	
	public int delete(long no) {
		return sqlSession.delete("reply.delete", no);
	}
	
	public int update(ReplyVo vo) {
		return sqlSession.update("reply.update", vo);
	}
	
}
