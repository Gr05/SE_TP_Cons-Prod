package jus.poc.prodcons.v5;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;
import jus.poc.prodcons.v5.MyObservateur;
import jus.poc.prodcons.v5.ProdCons;

public class Producteur extends Acteur implements _Producteur {

	//Il faut un ProdCons a Producteur
		private ProdCons tampon;
		private int nbMessage;
		
		protected Producteur(Observateur observateur, MyObservateur observator, int tempsMoyenProduction, int deviationTempsProduction, int nombreMoyenDeProduction, int deviationNombreMoyenDeProduction, ProdCons tampon) throws ControlException {
			//la première modif est ici, on utilise la doc d'Acteur et on utilise le static typeProducteur dans le constructeur d'Acteur.
			super(typeProducteur, observateur, tempsMoyenProduction, deviationTempsProduction);
			observateur.newProducteur(this);
			this.tampon = tampon;
			nbMessage = Aleatoire.valeur(nombreMoyenDeProduction, deviationNombreMoyenDeProduction);
			observator.ajouterMessage(nbMessage);
			
		}

	@Override
	public int nombreDeMessages() {
		return nbMessage;
	}
	
	public ProdCons tampon(){
		return tampon;
	}
	
	public void run(){
		for(int i = 1; i <= nombreDeMessages(); i++){
			int tempsDeTraitement =Aleatoire.valeur(50 * this.moyenneTempsDeTraitement(),50 * this.deviationTempsDeTraitement());
			MessageX m = new MessageX("Producteur " + identification() + " message numéro " + i);
			try {
				tampon().observateur().productionMessage(this, m, tempsDeTraitement);
				sleep(tempsDeTraitement);
				tampon().put(this, m);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
