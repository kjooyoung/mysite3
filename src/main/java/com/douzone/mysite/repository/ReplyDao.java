package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.ReplyVo;

@Repository
public class ReplyDao {
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insertReReply(ReplyVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "insert into reply values(null,?,now(),?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getContents());
			pstmt.setLong(2, vo.getBoardNo());
			pstmt.setLong(3, vo.getUserNo());
			pstmt.setLong(4, vo.getGroupNo());
			pstmt.setLong(5, vo.getOrderNo());
			pstmt.setLong(6, vo.getDepth());
			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (Exception e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	public boolean insert(ReplyVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "insert into reply values(null,?,now(),?,?, "
					+ " if((select max(a.g_no)+1 from (select g_no from reply) a) is null "
					+ " ,1,(select max(a.g_no)+1 from (select g_no from reply) a)), 1, 0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getContents());
			pstmt.setLong(2, vo.getBoardNo());
			pstmt.setLong(3, vo.getUserNo());
			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (Exception e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean updateOrder(ReplyVo momReplyVo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "update reply set o_no = o_no +1 " + 
					"where board_no = ? and g_no = ? " + 
					"and o_no >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, momReplyVo.getBoardNo());
			pstmt.setLong(2, momReplyVo.getGroupNo());
			pstmt.setLong(3, momReplyVo.getOrderNo());
			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (Exception e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	public long getRefOrderNo(ReplyVo momReply){
		long orderNo=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select min(o_no) from reply where depth <= ? and g_no = ? and o_no > ? and board_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, momReply.getDepth());
			pstmt.setLong(2, momReply.getGroupNo());
			pstmt.setLong(3, momReply.getOrderNo());
			pstmt.setLong(4, momReply.getBoardNo());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				orderNo = rs.getLong(1);
			}
			
		} catch (Exception e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(rs != null)rs.close();
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return orderNo;
	}
	public long getfffOrderNo(ReplyVo momReply){
		long orderNo=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select min(o_no) from reply where depth <= ? and g_no = ? and o_no > ? and board_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, momReply.getDepth());
			pstmt.setLong(2, momReply.getGroupNo());
			pstmt.setLong(3, momReply.getOrderNo());
			pstmt.setLong(4, momReply.getBoardNo());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				orderNo = rs.getLong(1);
			}
			
		} catch (Exception e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(rs != null)rs.close();
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return orderNo;
	}
	
	public long getMaxOrderNo(ReplyVo momReply){
		long orderNo=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select max(o_no)+1 from reply where board_no = ? and g_no = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, momReply.getBoardNo());
			pstmt.setLong(2, momReply.getGroupNo());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				orderNo = rs.getLong(1);
			}
			
		} catch (Exception e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(rs != null)rs.close();
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return orderNo;
	}
	
	public ReplyVo getReply(long no){
		ReplyVo vo = new ReplyVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select * from reply where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setNo(rs.getLong(1));
				vo.setContents(rs.getString(2));
				vo.setWriteDate(rs.getString(3));
				vo.setBoardNo(rs.getLong(4));
				vo.setUserNo(rs.getLong(5));
				vo.setGroupNo(rs.getInt(6));
				vo.setOrderNo(rs.getInt(7));
				vo.setDepth(rs.getInt(8));
			}
			
		} catch (Exception e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(rs != null)rs.close();
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return vo;
	}
	
	public List<ReplyVo> getList(long no){
		return sqlSession.selectList("reply.getList", no);
	}
	
	public boolean delete(long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "delete from reply where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (Exception e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean update(ReplyVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "update reply set contents = ? where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getContents());
			pstmt.setLong(2, vo.getNo());
			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (Exception e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:"+e);
		}
		return conn;
	}
}
