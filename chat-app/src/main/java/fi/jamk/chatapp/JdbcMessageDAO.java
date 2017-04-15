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
				"(message, user_iduser, chat_idchat) VALUES (?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, message.getMes());
			ps.setInt(2, message.getIduser());
			ps.setInt(3, message.getIdchat());
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

		String sql = "SELECT * FROM message";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			List<Message> messages = new ArrayList<Message>();
			Message message = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				message = new Message(
					rs.getString("message"),
					rs.getInt("user_iduser"),
					rs.getInt("chat_idchat")
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
	
}
