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
	MyObservateur observator;
	
	protected Consommateur(Observateur observateur, MyObservateur observator, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, ProdCons tampon) throws ControlException {
		//la première modif est ici, on utilise la doc d'Acteur et on utilise le static typeConsommateur dans le constructeur d'Acteur.
		super(typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.tampon = tampon;
		nbMessage = 0;
		this.observator = observator;
	}
	
	public ProdCons tampon(){
		return tampon;
	}
	
	public void run(){
		while(true){
			int tempsDeTraitement = Aleatoire.valeur(50 * this.moyenneTempsDeTraitement(), 50 * this.deviationTempsDeTraitement());
			try {
				sleep(tempsDeTraitement);
				System.out.println("Consommateur " + identification() + " a lu le message : '" + tampon().get(this).toString() +"' c'est le " + ++nbMessage + "ème message qu'il consomme.");
				observator.messageLu();
				//System.out.println(" il reste "  + observator.bilan());
			} catch (InterruptedException e) {
				interrupt();
				break;
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
