package jus.poc.prodcons.v4;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {
	
	private MessageX[] tampon;
	private int taille;
	private int indexToProd;
	private int indexToCons;
	private int nbPlein;
	private File fCons, fProd;
	private Observateur observateur;
	

	public ProdCons(int permits, Observateur observateur) {
		taille = permits;
		tampon = new MessageX[permits];
		fProd = new File();
		fCons = new File();
		indexToProd = 0;
		indexToCons = 0;
		nbPlein = 0;
		this.observateur = observateur;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int enAttente() {
		return nbPlein;
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		while (enAttente() == 0) fProd.attendre();
		tampon[indexToCons].traite();
		MessageX message = tampon[indexToCons];
		observateur().retraitMessage(arg0, message);
		if (message.nbExemplaire() == 0){
		nbPlein--;
		indexToCons = (indexToCons+1)%(taille);
		fCons.reveiller();
		}
		return message;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		while (enAttente() == taille) fCons.attendre();
		observateur().depotMessage(arg0, arg1);
		tampon[indexToProd] = (MessageX)arg1;
		nbPlein++;
		indexToProd = (indexToProd+1)%(taille);
		fProd.reveiller();
	}

	@Override
	public int taille() {
		return this.taille;
	}
	
	public Observateur observateur(){
		return this.observateur;
	}

}
