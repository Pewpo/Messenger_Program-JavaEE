package fi.jamk.chatapp;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
	private String mes;
	private String username;
	private int idchat;
	private Timestamp ts;
	
	public Message(){

	}
	
	public Message(String mes, String username, int idchat, Timestamp ts){
		this.mes = mes;
		this.username = username;
		this.idchat = idchat;
		this.ts = ts;
	}
	
	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getIdchat() {
		return idchat;
	}

	public void setIdchat(int idchat) {
		this.idchat = idchat;
	}
	
	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	public String toString(){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		SimpleDateFormat format2 = null;
		try {
			date = format.parse(ts.toString());
			format2 = new SimpleDateFormat("HH:mm dd.MM.yyyy ");
		} catch (ParseException e) { 
			e.printStackTrace();
		}
		
		return username + ": " + mes + "\n                   " + format2.format(date) + "\n";
	}
}
