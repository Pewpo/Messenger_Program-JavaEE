package fi.jamk.chatapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import sun.security.util.Password;


public class JdbcMessageDAO implements MessageDAO {
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void insert(Message message){

		String sql = "INSERT INTO messages " +
				"(ts, message, username) VALUES (?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, message.getTimestamp());
			ps.setString(2, message.getMes());
			ps.setString(3, message.getUser());
			ps.executeUpdate();
			ps.close();

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

	public List<Message> getAllMessages(){

		String sql = "SELECT * FROM messages";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			List<Message> messages = new ArrayList<Message>();
			Message message = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				message = new Message(
					rs.getTimestamp("ts"),
					rs.getString("message"),
					rs.getString("username")
				);
				messages.add(message);
			}
			rs.close();
			ps.close();
			return messages;
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
	//Trying to find user from database
	public boolean findUser(String name, String psw) {
		String sql = "SELECT * FROM messages WHERE username =  (?)" ;
		Connection conn = null;		
		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);						
			ResultSet rs = ps.executeQuery();
			if(!rs.next()){
				return false;
			}
			if(rs.next()){
				while (rs.next()) {
					return true;
				}
			}		
		}catch(SQLException e){}
		return false;		
	}
}
