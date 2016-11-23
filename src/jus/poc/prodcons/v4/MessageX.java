package jus.poc.prodcons.v4;

public class MessageX implements jus.poc.prodcons.Message{
	
	private String message;
	private int nbExemplaire;
	
	public MessageX(String s, int nbExemplaire){
		message = s;
		this.nbExemplaire = nbExemplaire;
	}
	
	public String toString(){
		return message;
	}
	
	public int nbExemplaire(){
		return nbExemplaire;
	}
	
	public void traite(){
		nbExemplaire--;
	}
}

