package com.cafe24.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.BoardVo;

@Repository
public class BoardDao {

	private static final int LIST_COUNT = 5;

	public BoardVo searchGet(String inputTitle) { // 들어갈 값이 많다면 vo 를 넣어서 사용한다.
		BoardVo result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String countBoardSql = "select count(no) from board where title like ?";

			pstmt = conn.prepareStatement(countBoardSql);
			pstmt.setString(1, "%" + inputTitle + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				result = new BoardVo();

				result.setMaxNo(rs.getLong(1));
				System.out.println("sadihalsdhaslkdjsakljd" + rs.getLong(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return result;

	}

	public List<BoardVo> search(String inputTitle) {
		List<BoardVo> list = new ArrayList<>();
		System.out.println("input : " + inputTitle);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select b.no, title, reg_date, hit, a.name, group_no, order_no, depth, user_no "
					+ "from users a, board b " + "where a.no=b.user_no " + "and title like ? "
					+ "order by group_no desc, order_no asc " + "limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + inputTitle + "%");
			pstmt.setLong(2, 0L);
			pstmt.setLong(3, 5L);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String curDate = rs.getString(3);
				Long hit = rs.getLong(4);
				String writer = rs.getString(5);

				Long groupNo = rs.getLong(6);
				Long orderNo = rs.getLong(7);
				Long depth = rs.getLong(8);

				Long userNo = rs.getLong(9);

				BoardVo vo = new BoardVo();

				vo.setNo(no);
				vo.setTitle(title);
				vo.setCurDate(curDate);
				vo.setHit(hit);
				vo.setUser(writer);

				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);

				vo.setUserNo(userNo);

				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return list;
	}

	public boolean hitUpdate(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "update board set hit = (select * from ((select hit from board  where no = ?)) a)+1 where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getNo());
			pstmt.setLong(2, vo.getNo());
			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return result;
	}

	public List<BoardVo> viewGetList(Long boardNo) {
		List<BoardVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select no, title, content, user_no from board where no = ?;";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, boardNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				Long userNo = rs.getLong(4);

				BoardVo vo = new BoardVo();

				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setUserNo(userNo);

				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return list;
	}

	public boolean update(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "update board set title = ?, content = ? where no = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());

			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return result;
	}

	public boolean delete(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = " delete from board where no = (select * from (select b.no from users a, board b where a.no = b.user_no and a.no = ? and b.no = ? and password = password(?)) a)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getUserNo());
			pstmt.setLong(2, vo.getNo());
			pstmt.setString(3, vo.getPassword());

			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return result;
	}

	public boolean insert(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "insert into board values(null, ?, ?, (select if(isnull(group_no), 1, max(group_no)+1) from board a), 1, 0, now(),0, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getUserNo());
			// pstmt.setLong(4, vo.getoNo());
			// pstmt.setLong(5, vo.getDepth());

			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return result;
	}

	public boolean replyUpdate(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "update board set order_no = order_no + 1 where group_no = ? and order_no > ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getGroupNo());
			pstmt.setLong(2, vo.getOrderNo());

			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return result;

	}

	public boolean replyInsert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 번호, 제목, 내용, 그룹번호, 그룹내 순서, 깊이, 날짜, 조회수, 회원번호
			String sql = "insert into board values(null, ?, ?, ?, ?, ?, now(),0, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getGroupNo());
			pstmt.setLong(4, vo.getOrderNo() + 1);
			pstmt.setLong(5, vo.getDepth() + 1);
			pstmt.setLong(6, vo.getUserNo());

			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return result;
	}

	public BoardVo get() { // 들어갈 값이 많다면 vo 를 넣어서 사용한다.
		BoardVo result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String countBoardSql = "select count(no) from board";
			pstmt = conn.prepareStatement(countBoardSql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				result = new BoardVo();

				result.setMaxNo(rs.getLong(1));
				System.out.println("sadihalsdhaslkdjsakljd" + rs.getLong(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return result;

	}

	// public List<BoardVo> getList(int page)
	public List<BoardVo> getList(int page) {
		List<BoardVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select b.no, title, reg_date, hit, a.name, group_no, order_no, depth, user_no "
					+ "from users a, board b " + "where a.no=b.user_no " + "order by group_no desc, order_no asc "
					+ "limit ?,?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, (page - 1) * LIST_COUNT);
			pstmt.setLong(2, LIST_COUNT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String curDate = rs.getString(3);
				Long hit = rs.getLong(4);
				String user = rs.getString(5);

				Long groupNo = rs.getLong(6);
				Long orderNo = rs.getLong(7);
				Long depth = rs.getLong(8);

				Long userNo = rs.getLong(9);

				BoardVo vo = new BoardVo();

				vo.setNo(no);
				vo.setTitle(title);
				vo.setCurDate(curDate);
				vo.setHit(hit);
				vo.setUser(user);

				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);

				vo.setUserNo(userNo);
				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return list;
	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			// 1. 드라이버 로딩
			Class.forName("com.mysql.jdbc.Driver");

			// 2.연결하기
			String url = "jdbc:mysql://localhost/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 " + e);
		}
		return conn;
	}

}
