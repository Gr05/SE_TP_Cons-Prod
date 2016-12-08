package jus.poc.prodcons.v5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {
	
	private MessageX[] tampon;
	private int taille, in, out, nbPlein;
	private Observateur observateur;
	private final Lock lock = new ReentrantLock();
    private final Condition prod = lock.newCondition(); 
    private final Condition cons = lock.newCondition();
    private final MySemaphore mutexOut = new MySemaphore(1);
    private final MySemaphore mutexIn = new MySemaphore(1);
    private final MySemaphore mutexNbPlein = new MySemaphore(1);
	

	public ProdCons(int permits, Observateur observateur) {
		taille = permits;
		tampon = new MessageX[permits];
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
		lock.lock();
		try {
			while (nbPlein == 0) cons.await();
			mutexOut.p();
			MessageX message = tampon[out];
			observateur.retraitMessage(arg0, message);
			System.out.println("Conso " + arg0.identification() + " --> retrait du message '" + message + "' à t = " + System.currentTimeMillis());
			out = (out+1)%(taille);
			mutexNbPlein.p();
			nbPlein--;
			mutexNbPlein.v();
			mutexOut.v();
			prod.signal();
			return message;
		} finally {
			lock.unlock();
	    }
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		lock.lock();
		try {
			while (nbPlein == taille) prod.await();
			mutexIn.p();
			observateur.depotMessage(arg0, arg1);
			System.out.println("Prod " + arg0.identification() + " --> depot du message : " + arg1 + " a t = " + System.currentTimeMillis());
			tampon[in] = (MessageX) arg1;
			in = (in+1)%(taille);
			mutexNbPlein.p();
			nbPlein++	;
			mutexNbPlein.v();
			mutexIn.v();
			cons.signal();
		} finally {
			lock.unlock();
		}
		
	}

	@Override
	public int taille() {
		return this.taille;
	}
	
	public Observateur observateur(){
		return this.observateur;
	}
}