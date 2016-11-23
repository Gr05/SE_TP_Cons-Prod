package jus.poc.prodcons.v3;

public class Message implements jus.poc.prodcons.Message{
	
	private String message;
	
	public Message(String s){
		message = s;
	}
	
	public String toString(){
		return message;
	}
}
