package fi.jamk.chatapp;

import java.sql.Timestamp;

public class Message {
	private String mes;
	private int iduser;
	private int idchat;
	
	
	public Message(){
		this.mes = "";
		this.iduser = 0;
		this.idchat = 0;
	}
	
	public Message(String mes, int iduser, int idchat){
		this.mes = mes;
		this.iduser = iduser;
		this.idchat = idchat;
	}
	
	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public int getIdchat() {
		return idchat;
	}

	public void setIdchat(int idchat) {
		this.idchat = idchat;
	}

	public String toString(){
		return mes + ", " + iduser + ": " + idchat + "\n";
	}
}
