package fi.jamk.chatapp;

import java.sql.Timestamp;

public class Message {
	private String mes;
	private String user;
	private Timestamp timestamp;
	
	
	public Message(){
		
	}
	
	public Message(Timestamp timestamp, String mes, String user){
		this.timestamp = timestamp;
		this.mes = mes;
		this.user = user;
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public String toString(){
		return timestamp + ", " + user + ": " + mes;
	}
}
