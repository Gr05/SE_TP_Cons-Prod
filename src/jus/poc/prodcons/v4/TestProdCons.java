package jus.poc.prodcons.v4;

import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;
import jus.poc.prodcons.Tampon;

public class TestProdCons extends Simulateur {
	
	private Consommateur[] C;
	private Producteur[] P;
	protected HashMap<String, Integer> option;
	private ProdCons tampon;

	public TestProdCons(Observateur observateur){
		super(observateur);
		option = new HashMap<String, Integer>();
		init("src/jus/poc/prodcons/options/option.xml");
		try {
			observateur.init(option.get("nbProd"), option.get("nbCons"), option.get("nbBuffer"));
		} catch (ControlException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.tampon = new ProdCons(option.get("nbBuffer"), observateur);
		C = new Consommateur[option.get("nbCons")];
		P = new Producteur[option.get("nbProd")];
		for(int i = 0; i< option.get("nbCons"); i++){
			try {
				C[i] = new Consommateur(observateur, option.get("tempsMoyenConsommation"), option.get("deviationTempsMoyenConsommation"), tampon);
			} catch (ControlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int i = 0; i< option.get("nbProd"); i++){
			try {
				P[i] = new Producteur(observateur, option.get("tempsMoyenProduction"), option.get("deviationTempsMoyenProduction"),
						option.get("nombreMoyenDeProduction"), option.get("deviationNombreMoyenDeProduction"),
						option.get("nombreMoyenNbExemplaire"), option.get("deviationNombreMoyenNbExemplaire"),
						tampon);
			} catch (ControlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO Auto-generated constructor stub
	}
	
	/**
	* Retreave the parameters of the application.
	* @param file the final name of the file containing the options.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws InvalidPropertiesFormatException 
	*/
	protected void init(String file){
		Properties properties = new Properties();
		try {
			properties.loadFromXML(new FileInputStream(file));
			//System.out.println(properties.get("nbBuffer"));
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("lol");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String key;
		int value;
		for(Map.Entry<Object,Object> entry : properties.entrySet()) {
			key = (String)entry.getKey();
			value = Integer.parseInt((String)entry.getValue());
			option.put(key, value);
		}
	}

	@Override
	protected void run() {
		for(int i = 0; i< option.get("nbProd"); i++){
			P[i].start();
		}
		for(int i = 0; i< option.get("nbCons"); i++){
			C[i].start();
		}
		while(Thread.activeCount() > option.get("nbCons") + 1 || tampon.enAttente() != 0){
			try {
				Thread.sleep(100);
				//System.out.println("nb message dans tampon =  " + tampon.enAttente());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Terminaison du programme, interruption des processus de consommation");
		for(int i = 0; i< option.get("nbCons"); i++){
			C[i].interrupt();
		}
	}
	
	public static void main(String[] args){
		new TestProdCons(new Observateur()).start();
	}
}