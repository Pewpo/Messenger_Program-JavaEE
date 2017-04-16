package fi.jamk.chatapp;

public interface ChatDAO {
	public int findChat(String userid1, String userid2);
	public void addNewChat(String userid1, String userid2);
}
