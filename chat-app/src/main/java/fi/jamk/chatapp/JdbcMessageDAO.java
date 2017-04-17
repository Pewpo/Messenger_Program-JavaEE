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

		String sql = "INSERT INTO message " +
				"(message, user_iduser, chat_idchat, ts) VALUES (?, ?, ?, ?)";
		
		String sql2 = "SELECT iduser FROM user WHERE username =(?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, message.getUsername());
			ResultSet rs = pstmt2.executeQuery();
			int userid = 0;
			if (rs.next()){
				userid = rs.getInt(1);
			}
			pstmt.setString(1, message.getMes()); 
			pstmt.setInt(2, userid);
			pstmt.setInt(3, message.getIdchat());
			pstmt.setTimestamp(4, message.getTs());
			pstmt.executeUpdate();
			pstmt.close();
			rs.close();

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

	public List<Message> getAllChatMessages(int chatid){
		String sql = "SELECT * FROM message WHERE chat_idchat=(?)";
		String sql2 = "SELECT username FROM user WHERE iduser=(?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatid);
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			List<Message> messages = new ArrayList<Message>();
			Message message = null;
			ResultSet rs = pstmt.executeQuery();
			
			String username = "";
			while (rs.next()) {
				pstmt2.setInt(1, rs.getInt("user_iduser"));
				ResultSet rs2 = pstmt2.executeQuery();
				if (rs2.next()){
					username = rs2.getString(1);
				}
				message = new Message(
					rs.getString("message"),
					username,
					rs.getInt("chat_idchat"),
					rs.getTimestamp("ts")
				);
				messages.add(message);
				rs2.close();
			}
			rs.close();
			pstmt.close();
			pstmt2.close();
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

}

