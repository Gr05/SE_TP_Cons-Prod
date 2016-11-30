package jus.poc.prodcons.v4;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {
	
	private MessageX[] tampon;
	private int taille, in, out;
	private MySemaphore mutexIn, mutexOut, prod, cons, mutexProd, mutexNbPlein;
	private Observateur observateur;
	private int nbPlein;
	

	public ProdCons(int permits, Observateur observateur) {
		taille = permits;
		tampon = new MessageX[permits];
		mutexIn = new MySemaphore(1);
		mutexOut = new MySemaphore(1);
		mutexProd = new MySemaphore(1);
		mutexNbPlein = new MySemaphore(1);
		prod = new MySemaphore(taille);
		cons = new MySemaphore(0);
		in = 0;
		out = 0;
		nbPlein = 0;
		this.observateur = observateur;
	}

	@Override
	public int enAttente() {
		return nbPlein;
	}

	@Override
	public MessageX get(_Consommateur arg0) throws Exception, InterruptedException {
		cons.p();
		mutexOut.p();
		mutexNbPlein.p();
		MessageX message = tampon[out];
		observateur.retraitMessage(arg0, message);
		System.out.println("Conso " + arg0.identification() + " --> retrait du message '" + message + "' à t = " + System.currentTimeMillis());
		out = (out+1)%(taille);
		nbPlein--;
		mutexNbPlein.v();
		mutexOut.v();
		prod.v();
		return message;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		prod.p();
		mutexIn.p();
		mutexNbPlein.p();
		observateur.depotMessage(arg0, arg1);
		System.out.println("Prod " + arg0.identification() + " --> depot du message : " + arg1 + " a t = " + System.currentTimeMillis());
		tampon[in] = (MessageX) arg1;
		in = (in+1)%(taille);
		nbPlein++;
		mutexNbPlein.v();
		mutexIn.v();
		cons.v();
	}
	
	public void put(_Producteur arg0, Message arg1, int nbExemplaire) throws Exception, InterruptedException {
		mutexProd.p();
		for(int i = 1; i <= nbExemplaire; i++){
			put(arg0, new MessageX(arg1, " Exemplaire " + i));
		}
		mutexProd.v();
	}

	@Override
	public int taille() {
		return this.taille;
	}
	
	public Observateur observateur(){
		return this.observateur;
	}
}