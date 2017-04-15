package fi.jamk.chatapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcUserDAO implements UserDAO {
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	//Check login credentials
	public boolean loginUser(User user) {
		String sql = "SELECT password FROM user WHERE username = (?)";
		Connection conn = null;
		String correctPassword = "";
		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getNickname());						
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				correctPassword = rs.getString(1);
			}
			System.out.println(correctPassword);
			ps.close();
			rs.close();

			if (user.getPassword().equals(correctPassword)){	
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}	
	}
	
	
	public boolean registerUser(User user){
		String sql = "SELECT username FROM user WHERE username = (?)";
		Connection conn = null;
		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getNickname());						
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				ps.close();
				rs.close();
				return false;
			}else{
				String sql2 = "INSERT INTO user(username, password) VALUES((?), (?));";
				PreparedStatement pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, user.getNickname());
				pstmt.setString(2, user.getPassword());
				int n = pstmt.executeUpdate();
				ps.close();
				rs.close();
				pstmt.close();
				return true;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}
}
