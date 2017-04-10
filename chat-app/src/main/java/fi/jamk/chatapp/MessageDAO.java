package fi.jamk.chatapp;
import sun.security.util.Password;

import java.sql.SQLException;
import java.util.List;

public interface MessageDAO {
	public void insert(Message message);
	public List<Message> getAllMessages();
	//Log in method
	public boolean findUser(String name, String psw);
}
