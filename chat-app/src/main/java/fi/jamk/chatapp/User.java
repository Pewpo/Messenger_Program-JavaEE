package fi.jamk.chatapp;


import sun.security.util.Password;

@SuppressWarnings("restriction")
public class User {
	
	private String nickname = null;
	private String password = null;
	
	public User(String nick, String pass){
		this.nickname = nick;
		this.password = pass;
	}
	
	public User(){
		this.nickname = "";
		this.password = null;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getPassword() {
		return  password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
