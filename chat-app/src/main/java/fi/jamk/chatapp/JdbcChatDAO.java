package fi.jamk.chatapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


public class JdbcChatDAO implements ChatDAO {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public int findChat(String uname1, String uname2){
		int apu = 0;
		Connection conn = null;
		try{
			String sql1 = "SELECT * FROM user_has_chat WHERE user_iduser = (SELECT iduser FROM user WHERE username = (?))";		
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql1);
			PreparedStatement ps2 = conn.prepareStatement(sql1);
			ps2.setString(1, uname2);		
			ps.setString(1, uname1);		
			ResultSet rs = ps.executeQuery();
			ResultSet rs2 = ps2.executeQuery();
			if (!rs.next() || !rs2.next()){
				apu = 0;
				rs.close();
				rs2.close();
			}else{
				rs.beforeFirst();
				rs2.beforeFirst();
				List<Chat_has_user> chats = new ArrayList<Chat_has_user>();
				while(rs.next()){			
					Chat_has_user c = new Chat_has_user();
					c.setChat_id(rs.getInt("chat_idchat"));
					c.setUser_id(rs.getInt("user_iduser"));	
					chats.add(c);			
				}
				while(rs2.next()){
					for(Chat_has_user chat  : chats){
						if(rs2.getInt("chat_idchat") == chat.getChat_id()){	
							apu = chat.getChat_id();
						}
					}
				}
				rs.close();
				rs2.close();
			}
			ps.close();
			ps2.close();
		}catch(SQLException e){ 
			System.out.println(e.getMessage());
			apu = 0;
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		return apu;
	}
	
	public void addNewChat(String uname1, String uname2) {
		Connection conn = null;
		String lastnumber = " Select MAX(idchat) FROM chat";
		String sql = "insert into user_has_chat(user_iduser, chat_idchat) values ((select iduser from user where username = (?)), (?))";
		try{
			conn = dataSource.getConnection();
			Statement s2 = conn.createStatement();
			s2.execute(lastnumber);    
			ResultSet rs = s2.getResultSet();
			int newid = 0;
			while(rs.next()){
				newid = rs.getInt(1) + 1;
			}
			s2.execute("INSERT INTO chat values (" + newid + ")");
			s2.execute("INSERT INTO user_has_chat(user_iduser, chat_idchat) VALUES ((SELECT iduser FROM user WHERE username = '" +uname1+"'), "+newid+")");
			s2.execute("INSERT INTO user_has_chat(user_iduser, chat_idchat) VALUES ((SELECT iduser FROM user WHERE username = '" + uname2 +"'), "+newid+")");
			s2.close();
			rs.close();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public int getChat(String uname1, String uname2){
		Connection conn = null;
		String sql = "SELECT chat_idchat FROM user_has_chat WHERE user_iduser = (SELECT iduser FROM user WHERE username =(?))"
				+ " OR user_iduser = (SELECT iduser FROM user WHERE username =(?)) GROUP BY chat_idchat HAVING COUNT(*) > 1;";
		int chatId = 0;
		try{
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uname1);		
			pstmt.setString(2, uname2);		
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()){
				chatId = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		return chatId;
	}
}
