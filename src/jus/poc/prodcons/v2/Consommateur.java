package jus.poc.prodcons.v2;


import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Consommateur;

public class Consommateur extends Acteur implements _Consommateur  {
	
	//Il faut un ProdCons a Consommateur
	private ProdCons tampon;
	private int nbMessage;
	
	protected Consommateur(Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, ProdCons tampon) throws ControlException {
		//la première modif est ici, on utilise la doc d'Acteur et on utilise le static typeConsommateur dans le constructeur d'Acteur.
		super(typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.tampon = tampon;
		nbMessage = 0;
	}
	
	public ProdCons tampon(){
		return tampon;
	}
	
	public void run(){
		while(tampon.enAttente() >= 0){
			int tempsDeTraitement = Aleatoire.valeur(50 * this.moyenneTempsDeTraitement(), 50 * this.deviationTempsDeTraitement());
			try {
				sleep(tempsDeTraitement);
				System.out.println("Consommateur " + identification() + " a lu le message : '" + tampon().get(this).toString() +"' c'est le " + ++nbMessage + "ème message qu'il consomme.");
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public int nombreDeMessages() {
		return nbMessage;
	}
}
