package fi.jamk.chatapp;

public interface ChatDAO {
	public int findChat(String uname1, String uname2);
	public void addNewChat(String uname1, String uname2);
	public int getChat(String uname1, String uname2);
}
