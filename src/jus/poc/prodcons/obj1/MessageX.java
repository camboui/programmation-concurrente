package jus.poc.prodcons.obj1;
import jus.poc.prodcons.*;


public class MessageX implements Message {

	String createur; // Permet de stocker qui à produit ce message
	
	public MessageX(String producteur){
		this.createur = producteur;
	}
	
	public String toString(){
		return createur;
	}
	
}
