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
	
	@Size(min=1, message = "Fill field first.")
	private String repassword = null;
	
	public User(String nick, String pass){
		this.nickname = nick;
		this.password = pass;
	}
	
	public User(String nick, String pass, String repass){
		this.nickname = nick;
		this.password = pass;
		this.repassword = repass;
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
	
	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	
	public String toString(){
		return nickname;
	}
}
