package jus.poc.prodcons.v3;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {
	
	private MessageX[] tampon;
	private int taille, in, out;
	private MySemaphore mutexIn, mutexOut, prod, cons;
	private Observateur observateur;
	private MyObservateur observator;
	

	public ProdCons(int permits, Observateur observateur, MyObservateur obervator) {
		taille = permits;
		tampon = new MessageX[permits];
		mutexIn = new MySemaphore(1);
		mutexOut = new MySemaphore(1);
		prod = new MySemaphore(taille);
		cons = new MySemaphore(0);
		in = 0;
		out = 0;
		this.observateur = observateur;
		this.observator = observator;
	}

	@Override
	public int enAttente() {
		return ((in - out)+taille) %taille;
	}

	@Override
	public MessageX get(_Consommateur arg0) throws Exception, InterruptedException {
		cons.p();
		mutexOut.p();
		MessageX message = tampon[out];
		tampon[out] = null;
		if (message != null){
			observateur.retraitMessage(arg0, message);
			System.out.println("Conso " + arg0.identification() + " --> retrait du message '" + message + "' à t = " + System.currentTimeMillis());
		}
		out = (out+1)%(taille);
		mutexOut.v();
		prod.v();
		return message;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		prod.p();
		mutexIn.p();
		observateur.depotMessage(arg0, arg1);
		System.out.println("Prod " + arg0.identification() + " --> depot du message : " + arg1 + " a t = " + System.currentTimeMillis());
		tampon[in] = (MessageX) arg1;
		in = (in+1)%(taille);
		mutexIn.v();
		cons.v();
	}

	@Override
	public int taille() {
		return this.taille;
	}
	
	public Observateur observateur(){
		return this.observateur;
	}
}