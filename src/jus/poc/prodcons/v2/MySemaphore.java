package jus.poc.prodcons.v2;

public class MySemaphore {
	
	private int cpt; //Nombre de processus actif dans le cas ou cpt est positif, Nombre de Processus en attente dans le cas ou cpt est négatif
	
	public MySemaphore(int n){
		this.cpt = n;
	}
	
	public synchronized void p(){
		if(--cpt < 0){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void v(){
	if( ++cpt <= 0) notify();
	}
}
