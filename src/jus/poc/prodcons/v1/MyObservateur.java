package jus.poc.prodcons.v1;

import java.util.concurrent.Semaphore;

public class MyObservateur{
	
	private MySemaphore mutex;
	private int nbMessageALire = 0;
	private int nbMessageLu = 0;
	
	public MyObservateur(){
		mutex = new MySemaphore(1);
	}
	
	public void ajouterMessage(int n){
		nbMessageALire += n ;
	}
	
	public void messageLu(){
		mutex.p();
		nbMessageLu++;
		mutex.v();
	}
	
	public int bilan(){
		return nbMessageALire - nbMessageLu;
	}
	
	public boolean done(){
		return (nbMessageALire == nbMessageLu);
	}
}
