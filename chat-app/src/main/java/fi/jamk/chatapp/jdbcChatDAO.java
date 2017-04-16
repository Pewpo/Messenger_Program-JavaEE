package fi.jamk.chatapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


public class jdbcChatDAO implements ChatDAO {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public int findChat(String userid1, String userid2){
		int apu = 0;
		Connection conn = null;
		try{
		
		String sql1 = "select* from user_has_chat where user_iduser = (select iduser from user where username = (?))";		
		conn = dataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql1);
		PreparedStatement ps2 = conn.prepareStatement(sql1);
		ps2.setString(1, userid2);		
		ps.setString(1, userid1);		
		ResultSet rs = ps.executeQuery();
		ResultSet rs2 = ps2.executeQuery();
		if(!rs.next() || !rs2.next()){
			apu = 0;
			rs.close();
			rs2.close();
		}
		else{
			rs.beforeFirst();
			rs2.beforeFirst();
			List<Chat_has_user> chats = new ArrayList();
			while(rs.next()){			
				Chat_has_user c = new Chat_has_user();
				c.setChat_id(rs.getInt("chat_idchat"));
				c.setUser_id(rs.getInt("user_iduser"));	
				chats.add(c);			
			}
			while(rs2.next()){
				for(Chat_has_user chat  : chats){
					if(rs2.getInt("chat_idchat") == chat.getChat_id()){	
						apu =  chat.getChat_id();
					}
				}
			}
			rs.close();
			rs2.close();
		}
		ps.close();
		ps2.close();
		conn.close();
	}
	catch(SQLException e)
		{ 
			System.out.println(e.getMessage());
			apu = 0;
		}		
		return apu;
	}
	
	public void addNewChat(String uname1, String uname2) {
		Connection conn = null;
		String lastnumber = " Select MAX(idchat) from chat";
		String sql = "insert into user_has_chat(user_iduser, chat_idchat) values ((select iduser from user where username = (?)), (?))";
		try{
		conn = dataSource.getConnection();
		Statement s2 = conn.createStatement();
		s2.execute(lastnumber);    
		ResultSet rs = s2.getResultSet(); // 
		int newid = 0;
		while(rs.next()){
			newid = rs.getInt(1) + 1;
		}
		s2.execute("insert into chat values (" + newid + ")");
		s2.execute("insert into user_has_chat(user_iduser, chat_idchat) values ((select iduser from user where username = '" +uname1+"'), "+newid+")");
		s2.execute("insert into user_has_chat(user_iduser, chat_idchat) values ((select iduser from user where username = '" + uname2 +"'), "+newid+")");
		s2.close();
		conn.close();
		rs.close();		
		conn.close();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}

}
