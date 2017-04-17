package fi.jamk.chatapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<User> getAllUsers(String loggedAs){
		List<User> allUsers = new ArrayList<User>();
		String sql = "SELECT username FROM user WHERE NOT username = (?) ORDER BY username ASC";
		Connection conn = null;
		try{
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loggedAs);						
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				User user = new User();
				user.setNickname(rs.getString(1));
				allUsers.add(user);
			}
			pstmt.close();
			rs.close();
			return allUsers;
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
