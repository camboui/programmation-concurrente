package jus.poc.prodcons.v3;
import jus.poc.prodcons.*;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.ArrayList;
import java.util.Properties;

public class TestProdCons extends Simulateur {

	int nbProd,nbCons,nbBuffer;
	int nombreMoyenDeProduction,deviationNombreMoyenDeProduction;
	int tempsMoyenProduction, deviationTempsMoyenProduction;
	int tempsMoyenConsommation,deviationTempsMoyenConsommation;
	boolean inhiber;

    protected void init(String file) {
        Properties properties = new Properties();
        
        try {
			properties.loadFromXML(ClassLoader.getSystemResourceAsStream(file));
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //On parse le fichier pour récupérer les valeurs des paramètres de simulation
        nbProd= Integer.parseInt(properties.getProperty("nbProd"));
        nbCons= Integer.parseInt(properties.getProperty("nbCons"));
        nbBuffer= Integer.parseInt(properties.getProperty("nbBuffer"));
        tempsMoyenProduction=Integer.parseInt(properties.getProperty("tempsMoyenProduction"));
        deviationNombreMoyenDeProduction=Integer.parseInt(properties.getProperty("deviationNombreMoyenDeProduction"));
        tempsMoyenConsommation=Integer.parseInt(properties.getProperty("tempsMoyenConsommation"));
        deviationTempsMoyenConsommation=Integer.parseInt(properties.getProperty("deviationTempsMoyenConsommation"));
        nombreMoyenDeProduction=Integer.parseInt(properties.getProperty("nombreMoyenDeProduction"));
        deviationTempsMoyenProduction=Integer.parseInt(properties.getProperty("deviationTempsMoyenProduction"));
        inhiber=Boolean.parseBoolean(properties.getProperty("inhiber"));
    }
    
	public TestProdCons(Observateur observateur) {
		super(observateur);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void run() throws Exception {
		init("./jus/poc/prodcons/options/options.xml");
		ProdCons data = new ProdCons(nbBuffer);
		ArrayList<Producteur> lesProds = new ArrayList<Producteur>();
		ArrayList<Consommateur> lesCons = new ArrayList<Consommateur>();
		Aleatoire toProduce = new Aleatoire(nombreMoyenDeProduction, deviationNombreMoyenDeProduction);
		
		// Class obs
		observateur.init(nbProd, nbCons, nbBuffer);
		
		//Initialiser les prod
		for(int i=0;i<nbProd;i++)
		{
			lesProds.add(new Producteur(observateur, tempsMoyenProduction, deviationNombreMoyenDeProduction, data, toProduce.next()));
		}
		
		//initialiser les cons
		for(int i=0;i<nbCons;i++)
		{
			lesCons.add(new Consommateur(observateur, tempsMoyenConsommation, deviationTempsMoyenConsommation, data));
		}

		//les faire communiquer
		for(int i=0 ; i<lesProds.size() ; i++){
			lesProds.get(i).start();
		}
		for(int i=0 ; i<lesCons.size() ; i++){
			lesCons.get(i).start();
		}
		
		//On attend pour terminer les consommateurs
		for(int i=0 ; i<lesProds.size() ; i++){
			lesProds.get(i).join();
		}
		
		//On attend que tous producteurs soits lus
		while(data.enAttente()!=0){}
		
		//On termine tous les consommateurs
		for(int i=0 ; i<lesCons.size() ; i++){
			lesCons.get(i).stop();
		}

		
	}
	
	public static void main(String[] args){
		new TestProdCons(new Observateur()).start();
	}
   
}
