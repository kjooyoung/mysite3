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

@Repository
public class BoardDao {
	public List<BoardVo> getList(String kwd, int pageStart, int perPageNum){
		List<BoardVo> list = new ArrayList<BoardVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select distinct a.no, a.title, a.contents, "
					+ "date_format(a.write_date,'%Y-%m-%d %h:%i:%s'), a.hit, a.g_no, a.o_no, a.depth, " + 
					"a.user_no, (select name from user where no = user_no), "
					+ "(select count(*) from reply where board_no = a.no) "
					+ "from board a, user b where a.user_no = b.no " + 
					"and a.title like ? or a.contents like ? order by g_no desc, o_no asc "
					+ "limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+kwd+"%");
			pstmt.setString(2, "%"+kwd+"%");
			pstmt.setInt(3, pageStart);
			pstmt.setInt(4, perPageNum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setWriteDate(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setGroupNo(rs.getInt(6));
				vo.setOrderNo(rs.getInt(7));
				vo.setDepth(rs.getInt(8));
				vo.setUserNo(rs.getInt(9));
				vo.setUserName(rs.getString(10));
				vo.setReplyCount(rs.getInt(11));
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
			
	public BoardVo getBoard(long no){
		BoardVo vo = new BoardVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select * from board where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setWriteDate(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setGroupNo(rs.getInt(6));
				vo.setOrderNo(rs.getInt(7));
				vo.setDepth(rs.getInt(8));
				vo.setUserNo(rs.getInt(9));
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
	
	public boolean insert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "insert into board values(null, ?, ?, now(), 0,"
					+ " if((select max(a.g_no)+1 from (select g_no from board) a) is null,1,(select max(a.g_no)+1 from (select g_no from board) a)), 1, 0, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
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
	
	public boolean insert(long no, BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "insert into board values(null, ?, ?, now(), 0, " + 
					"(select a.g_no from (select g_no from board where no = ?) a ), " + 
					"(select a.o_no from (select o_no from board where no = ?) a)+1, " + 
					"(select a.depth from (select depth from board where no=?) a)+1, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, no);
			pstmt.setLong(4, no);
			pstmt.setLong(5, no);
			pstmt.setLong(6, vo.getUserNo());
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
	
	public boolean updateOrder(long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "update board set o_no = o_no +1 " + 
					"where g_no = (select a.g_no from (select g_no from board where no = ?) a) " + 
					"and o_no > (select b.o_no from (select o_no from board where no = ?) b)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
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
	
	public boolean update(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "update board set title = ?, contents = ? where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());
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
	
	public boolean updateHit(long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "update board set hit = hit + 1 where no = ?";
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
	
	public boolean delete(long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "delete from board where no = ?";
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
	
	public int getTotalCount(String kwd) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select count(*) from board "
					+ "where title like ? or contents like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+kwd+"%");
			pstmt.setString(2, "%"+kwd+"%");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}
	
	public int getTotalCount() {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select count(*) from board ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
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
		return count;
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
