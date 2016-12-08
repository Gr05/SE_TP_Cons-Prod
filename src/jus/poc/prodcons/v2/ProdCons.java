package jus.poc.prodcons.v2;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {
	
	private MessageX[] tampon;
	private int taille, in, out;
	private MySemaphore mutexIn, mutexOut, prod, cons;
	

	public ProdCons(int permits) {
		taille = permits;
		tampon = new MessageX[permits];
		mutexIn = new MySemaphore(1);
		mutexOut = new MySemaphore(1);
		prod = new MySemaphore(taille);
		cons = new MySemaphore(0);
		in = 0;
		out = 0;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int enAttente() {
		return ((in - out)+taille) %taille;
	}

	public MessageX get(_Consommateur arg0) throws Exception, InterruptedException {
		cons.p();
		mutexOut.p();
		MessageX message = tampon[out];
		tampon[out] = null;
		if (message != null){
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
		System.out.println("Prod " + arg0.identification() + " --> depot du message : " + arg1 + " à t = " + System.currentTimeMillis());
		tampon[in] = (MessageX) arg1;
		in = (in+1)%(taille);
		mutexIn.v();
		cons.v();
	}

	@Override
	public int taille() {
		return this.taille;
	}

}