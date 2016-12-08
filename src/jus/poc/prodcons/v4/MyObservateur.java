package jus.poc.prodcons.v4;

public class MyObservateur{
	
	private MySemaphore mutex;
	private int nbProd;
	
	public MyObservateur(int nbProd){
		mutex = new MySemaphore(1);
		this.nbProd = nbProd;
	}
	
	public void prodFini(){
		mutex.p();
		nbProd--;
		mutex.v();
	}
	
	public int bilan(){
		return nbProd;
	}
	
	public boolean done(){
		return (nbProd == 0);
	}
}
