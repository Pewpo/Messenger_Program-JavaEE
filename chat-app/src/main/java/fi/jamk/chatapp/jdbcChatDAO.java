package fi.jamk.chatapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		try{
		Connection conn = null;
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
		}			
	}
	catch(SQLException e)
		{ 
		System.out.println("t‰‰lll‰mysql");
			System.out.println(e.getMessage());
			apu = 0;
		}
		return apu;
	}
	
	public void addNewChat() {
		// TODO Auto-generated method stub
		
	}

}
