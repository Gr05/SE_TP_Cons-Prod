package jus.poc.prodcons.v6;

import java.util.Vector;

public class MyObservateur{

	private int debug;
	private int nbMessageProduit = 0;
	private int nbMessageALire = 0;
	private int nbMessageCons = 0;
	private Vector<Producteur> P;
	private Vector<Consommateur> C;
	private Vector<MessageX> messageCons;
	private Vector<MessageX> messageProd;
	
	public MyObservateur(int debug){
		messageCons = new Vector<MessageX>();
		messageProd = new Vector<MessageX>();
		C = new Vector<Consommateur>();
		P = new Vector<Producteur>();
		this.debug = debug;
		
	}
	
	public void ajouterMessage(int n){
		nbMessageALire += n ;
	}
	
	public synchronized void messageLu(){
		nbMessageCons++;
	}
	
	public int bilan(){
		return nbMessageALire - nbMessageCons;
	}
	
	public boolean done(){
		return (nbMessageALire == nbMessageCons);
	}
	
	public void newConsommateur(Consommateur c){	
		C.add(c);
	}
	
	public void newProducteur(Producteur p){	
		P.add(p);
	}
	
	public void depotMessage(MessageX m){
		messageProd.add(m);
	}
	
	public void retraitMessage(MessageX m){
		messageCons.add(m);
	}
	
	public boolean debug(){
		return debug != 0;
	}
	
	public void verif(){
		boolean prodFini = true;
		for (Producteur p: P){
			prodFini = prodFini && !p.isAlive();
		}
		boolean consFini = true;
		for (Consommateur c: C){
			consFini = consFini && !c.isAlive();
		}
		boolean nbMessageRespecter = (messageCons.size() == messageProd.size());
		if (!nbMessageRespecter){
			System.out.println("Le nombre de message consommé et produit n'est pas le même il y a un problème");
		}
		else {
			boolean ordreRespecter = true;
			for(int i = 0; i < messageCons.size(); i++){
				ordreRespecter = ordreRespecter && (messageCons.get(i) == messageProd.get(i));
			}
			if (prodFini && consFini && ordreRespecter){
				System.out.println("L'application a fonctionné correctement.");
			} else {
				System.out.println("ERROR :: ProdFini = " + prodFini + ", ConsFini : " + consFini + ", OrdreRespecter = " + ordreRespecter );
			}
		}
	}
}
