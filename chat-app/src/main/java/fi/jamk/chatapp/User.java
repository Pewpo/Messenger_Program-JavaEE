package fi.jamk.chatapp;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import sun.security.util.Password;

@SuppressWarnings("restriction")
public class User {
	
	
	@Size(min=1, message = "Fill field first.")
	private String nickname = null;

	@Size(min=1, message = "Fill field first.")
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
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
