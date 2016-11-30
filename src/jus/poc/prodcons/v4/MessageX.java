package jus.poc.prodcons.v4;

import jus.poc.prodcons.Message;

public class MessageX implements jus.poc.prodcons.Message{
	
	private String message;
	
	public MessageX(Message arg1, String s){
		this.message = arg1.toString() + s;
	}
	
	public MessageX(String s){
		message = s;
	}
	
	public String toString(){
		return message;
	}
}
