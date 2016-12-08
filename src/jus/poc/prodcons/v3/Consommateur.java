package jus.poc.prodcons.v3;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons.v3.MyObservateur;
import jus.poc.prodcons.v3.ProdCons;

public class Consommateur extends Acteur implements _Consommateur  {
	
	//Il faut un ProdCons a Consommateur
		private ProdCons tampon;
		private int nbMessage;
		MyObservateur observator;
		
		protected Consommateur(Observateur observateur, MyObservateur observator, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, ProdCons tampon) throws ControlException {
			//la première modif est ici, on utilise la doc d'Acteur et on utilise le static typeConsommateur dans le constructeur d'Acteur.
			super(typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
			observateur.newConsommateur(this);
			this.tampon = tampon;
			nbMessage = 0;
			this.observator = observator;
		}

	@Override
	public int nombreDeMessages() {
		return nbMessage;
	}
	
	public ProdCons tampon(){
		return tampon;
	}
	
	public void run(){
		while(true){
			int tempsDeTraitement =Aleatoire.valeur(50 * this.moyenneTempsDeTraitement(),50 * this.deviationTempsDeTraitement());
			try {
				sleep(tempsDeTraitement);
				MessageX m = tampon().get(this);
				if (m != null){
					tampon().observateur().consommationMessage(this, m, tempsDeTraitement);
					observator.messageLu();
				}
				
			} catch (InterruptedException e) {
				interrupt();
				break;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}

