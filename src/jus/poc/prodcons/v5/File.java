package jus.poc.prodcons.v5;

public class File {
	
	public File(){
		
	}
	
	public synchronized void attendre(){try {
		wait();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}}
	
	public synchronized void reveiller(){notify();}
}
