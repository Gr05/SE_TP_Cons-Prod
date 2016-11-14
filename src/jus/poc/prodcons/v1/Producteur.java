package jus.poc.prodcons.v1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur {

	//Il faut un ProdCons a Producteur
	private ProdCons s;
	
	protected Producteur(Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, ProdCons s) throws ControlException {
		//la première modif est ici, on utilise la doc d'Acteur et on utilise le static typeProducteur dans le constructeur d'Acteur.
		super(typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.s = s;
	}

	@Override
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public ProdCons ProdCons(){
		return s;
	}
}
