package jus.poc.prodcons.v2;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {
	
	private Message[] tampon;
	private int taille;
	private int indexToProd;
	private int indexToCons;
	private int nbPlein;
	private File fCons, fProd;
	

	public ProdCons(int permits) {
		taille = permits;
		tampon = new Message[permits];
		fProd = new File();
		fCons = new File();
		indexToProd = 0;
		indexToCons = 0;
		nbPlein = 0;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int enAttente() {
		return nbPlein;
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		while (enAttente() == 0) fProd.attendre();
		Message message = tampon[indexToCons];
		nbPlein--;
		indexToCons = (indexToCons+1)%(taille);
		fCons.reveiller();
		return message;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		while (enAttente() == taille) fCons.attendre();
		tampon[indexToProd] = arg1;
		nbPlein++;
		indexToProd = (indexToProd+1)%(taille);
		fProd.reveiller();
	}

	@Override
	public int taille() {
		return this.taille;
	}

}