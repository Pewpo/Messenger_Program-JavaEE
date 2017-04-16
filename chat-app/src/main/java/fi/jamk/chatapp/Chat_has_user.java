package fi.jamk.chatapp;

public class Chat_has_user {
	private int user_id;
	private int chat_id;
	public Chat_has_user(){}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getChat_id() {
		return chat_id;
	}
	public void setChat_id(int chat_id) {
		this.chat_id = chat_id;
	}
	@Override
	public String toString() {
		return "Chat [user_id=" + user_id + ", chat_id=" + chat_id + "]";
	}

}
