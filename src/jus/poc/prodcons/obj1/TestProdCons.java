package jus.poc.prodcons.obj1;
import jus.poc.prodcons.*;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.ArrayList;
import java.util.Properties;

public class TestProdCons extends Simulateur {

	int nbProd,nbCons,nbBuffer,nombreMoyenDeProduction;
    int deviationNombreMoyenDeProduction,nombreMoyenDeConsommation;
    int deviationNombreMoyenDeConsommation,tempsMoyenProduction;
    int deviationTempsMoyenProduction,tempsMoyenConsommation;
    int deviationTempsMoyenConsommation;
    boolean inhiber;

    protected int option;

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
		// TODO Auto-generated method stub
		init("./jus/poc/prodcons/options/options.xml");
	}
	
	public static void main(String[] args){
		System.out.println("exec");

		new TestProdCons(new Observateur()).start();
	}
   
}
