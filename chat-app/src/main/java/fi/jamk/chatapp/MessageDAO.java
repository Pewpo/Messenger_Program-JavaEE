package fi.jamk.chatapp;

import java.util.List;

public interface MessageDAO {
	public void insert(Message message);
	public List<Message> getAllMessages();
}
