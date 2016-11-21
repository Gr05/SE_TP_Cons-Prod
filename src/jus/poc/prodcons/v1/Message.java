package jus.poc.prodcons.v1;

public class Message implements jus.poc.prodcons.Message{
	
	private String message;
	
	public Message(String s){
		message = s;
	}
	
	public String toString(){
		return message;
	}
}
