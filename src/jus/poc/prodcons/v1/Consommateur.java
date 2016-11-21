package jus.poc.prodcons.v1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Consommateur;

public class Consommateur extends Acteur implements _Consommateur  {
	
	//Il faut un ProdCons a Consommateur
	private ProdCons tampon;
	private int nbMessage;
	
	protected Consommateur(Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, int nombreMoyenNbExemplaire, int deviationNombreMoyenNbExemplaire, ProdCons tampon) throws ControlException {
		//la première modif est ici, on utilise la doc d'Acteur et on utilise le static typeConsommateur dans le constructeur d'Acteur.
		super(typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.tampon = tampon;
		nbMessage = Aleatoire.valeur(nombreMoyenNbExemplaire, deviationNombreMoyenNbExemplaire);
	}

	@Override
	public int nombreDeMessages() {
		return nbMessage;
	}
	
	public ProdCons tampon(){
		return tampon;
	}
	
	public void run(){
		for (int i = 1; i <= nombreDeMessages(); i++){
			try {
				sleep(100 * Aleatoire.valeur(this.moyenneTempsDeTraitement(), this.deviationTempsDeTraitement()));
				System.out.println("Consommateur : " + identification() + " a lu le message :" + tampon().get(this).toString());
				
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
