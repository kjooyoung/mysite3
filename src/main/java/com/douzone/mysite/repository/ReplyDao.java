package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.ReplyVo;

@Repository
public class ReplyDao {
	
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
	
	public boolean insert(long replyNo, ReplyVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "insert into reply values(null, ?, now(),?, ?, " + 
					"(select a.g_no from (select g_no from reply where no = ?) a ), " + 
					"(select max(o_no) from (select o_no from reply where g_no = (select g_no from reply where no = ?)) a)+1, " + 
					"(select a.depth from (select depth from reply where no=?) a)+1)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getContents());
			pstmt.setLong(2, vo.getBoardNo());
			pstmt.setLong(3, vo.getUserNo());
			pstmt.setLong(4, replyNo);
			pstmt.setLong(5, replyNo);
			pstmt.setLong(6, replyNo);
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
	
	public boolean insert(ReplyVo refReply, ReplyVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "insert into reply values(null, ?, now(),?, ?, " + 
					"?, ? , ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getContents());
			pstmt.setLong(2, vo.getBoardNo());
			pstmt.setLong(3, vo.getUserNo());
			pstmt.setLong(4, refReply.getGroupNo());
			pstmt.setLong(5, refReply.getOrderNo());
			pstmt.setLong(6, refReply.getDepth()+1);
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
	
//	public boolean insert(long replyNo, long orderNo, ReplyVo vo) {
//		boolean result = false;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = getConnection();
//			String sql = "insert into reply values(null, ?, now(),?, ?, " + 
//					"(select a.g_no from (select g_no from reply where no = ?) a ), " + 
//					"? , " + 
//					"(select a.depth from (select depth from reply where no=?) a)+1)";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, vo.getContents());
//			pstmt.setLong(2, vo.getBoardNo());
//			pstmt.setLong(3, vo.getUserNo());
//			pstmt.setLong(4, replyNo);
//			pstmt.setLong(5, orderNo);
//			pstmt.setLong(6, replyNo);
//			int count = pstmt.executeUpdate();
//			result = count == 1;
//		} catch (Exception e) {
//			System.out.println("error:"+e);
//		} finally {
//			try {
//				if(pstmt != null)pstmt.close();
//				if(conn != null)conn.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		return result;
//	}
	
//	public boolean updateOrder(ReplyVo momReply) {
//		boolean result = false;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = getConnection();
//			String sql = "update reply set o_no = o_no +1 " + 
//					"where g_no = (select a.g_no from (select g_no from reply where no = ?) a) " + 
//					"and o_no >= (select b.o_no from (select min(o_no) as o_no from reply where depth = ? and g_no = ? and o_no > ? and board_no = ?) b)";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setLong(1, momReply.getGroupNo());
//			pstmt.setLong(2, momReply.getDepth());
//			pstmt.setLong(3, momReply.getGroupNo());
//			pstmt.setLong(4, momReply.getOrderNo());
//			pstmt.setLong(5, momReply.getBoardNo());
//			int count = pstmt.executeUpdate();
//			result = count == 1;
//		} catch (Exception e) {
//			System.out.println("error:"+e);
//		} finally {
//			try {
//				if(pstmt != null)pstmt.close();
//				if(conn != null)conn.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		return result;
//	}
	
	public boolean updateOrder(ReplyVo refReply) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "update reply set o_no = o_no +1 " + 
					"where board_no = ? and g_no = ? " + 
					"and o_no >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, refReply.getBoardNo());
			pstmt.setLong(2, refReply.getGroupNo());
			pstmt.setLong(3, refReply.getOrderNo());
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
	
	public long getOrderNo(ReplyVo momReply){
		long orderNo=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select min(o_no) from reply where depth > ? and g_no = ? and o_no > ? and board_no = ?";
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
	
	public ReplyVo getMomRefOrderNo(ReplyVo momReply){
		ReplyVo vo = new ReplyVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select * from reply where board_no = ? and g_no = ? " + 
					" and o_no > ? and depth = ? order by o_no limit 0,1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, momReply.getBoardNo());
			pstmt.setLong(2, momReply.getGroupNo());
			pstmt.setLong(3, momReply.getOrderNo());
			pstmt.setLong(4, momReply.getDepth()-1);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setNo(rs.getLong(1));
				vo.setContents(rs.getString(2));
				vo.setWriteDate(rs.getString(3));
				vo.setBoardNo(rs.getInt(4));
				vo.setUserNo(rs.getInt(5));
				vo.setGroupNo(rs.getInt(6));
				vo.setOrderNo(rs.getInt(7));
				vo.setDepth(rs.getInt(8));
			} else {
				vo.setOrderNo(0);
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
	
	public ReplyVo getRefOrderNo(ReplyVo momReply){
		ReplyVo vo = new ReplyVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select * from reply where board_no = ? and g_no = ? " + 
					" and o_no > ? and depth = ? order by o_no limit 0,1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, momReply.getBoardNo());
			pstmt.setLong(2, momReply.getGroupNo());
			pstmt.setLong(3, momReply.getOrderNo());
			pstmt.setLong(4, momReply.getDepth());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setNo(rs.getLong(1));
				vo.setContents(rs.getString(2));
				vo.setWriteDate(rs.getString(3));
				vo.setBoardNo(rs.getInt(4));
				vo.setUserNo(rs.getInt(5));
				vo.setGroupNo(rs.getInt(6));
				vo.setOrderNo(rs.getInt(7));
				vo.setDepth(rs.getInt(8));
			} else {
				vo.setOrderNo(0);
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
		List<ReplyVo> list = new ArrayList<ReplyVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select a.no, a.contents, date_format(a.write_date,'%Y-%m-%d %h:%i:%s'), a.board_no, "
					+ " a.user_no, b.name, a.g_no, a.o_no, a.depth from reply a, user b "
					+ " where a.user_no = b.no "
					+ " and a.board_no = ? order by a.g_no asc, o_no asc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReplyVo vo = new ReplyVo();
				vo.setNo(rs.getLong(1));
				vo.setContents(rs.getString(2));
				vo.setWriteDate(rs.getString(3));
				vo.setBoardNo(rs.getLong(4));
				vo.setUserNo(rs.getLong(5));
				vo.setUserName(rs.getString(6));
				vo.setGroupNo(rs.getLong(7));
				vo.setOrderNo(rs.getLong(8));
				vo.setDepth(rs.getLong(9));
				list.add(vo);
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
		return list;
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
	
	public boolean update(long no, String contents) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "update reply set contents = ? where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, contents);
			pstmt.setLong(2, no);
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
