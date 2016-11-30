package jus.poc.prodcons.v1;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {
	
	private MessageX[] tampon;
	private int taille;
	private int indexToProd;
	private int indexToCons;
	private int nbPlein;

	public ProdCons(int permits) {
		taille = permits;
		tampon = new MessageX[permits];
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
	public synchronized MessageX get(_Consommateur arg0) throws Exception, InterruptedException {
		while (enAttente() == 0) wait();
		MessageX message = tampon[indexToCons];
		System.out.println("Conso " + arg0.identification() + " --> retrait du message '" + message + "' à t = " + System.currentTimeMillis());
		nbPlein--;
		indexToCons = (indexToCons+1)%(taille);
		notifyAll();
		return message;
	}

	@Override
	public synchronized void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		while (enAttente() >= taille) wait();
		tampon[indexToProd] = (MessageX) arg1;
		System.out.println("Prod " + arg0.identification() + " --> depot du message : " + arg1 + " à t = " + System.currentTimeMillis());
		nbPlein++;
		indexToProd = (indexToProd+1)%(taille);
		notifyAll();
	}

	@Override
	public int taille() {
		return this.taille;
	}

}
