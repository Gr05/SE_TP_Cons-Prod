package jus.poc.prodcons.v2;

public class MessageX implements jus.poc.prodcons.Message{
	
	private String message;
	
	public MessageX(String s){
		message = s;
	}
	
	public String toString(){
		return message;
	}
}
