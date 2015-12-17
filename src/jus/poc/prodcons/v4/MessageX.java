package jus.poc.prodcons.v4;
import jus.poc.prodcons.*;

 
public class MessageX implements Message {

	int idProd; // Permet de stocker qui à produit ce message
	int messNum;
	int nbMessage; // nombre d'exemplaires
	
	public MessageX(int idProd, int messNum, int nbMessage){
		this.idProd = idProd;
		this.messNum = messNum;
		this.nbMessage = nbMessage;
	}
	
	public String toString(){
		return "produit " + idProd + " message " + messNum;
	}
	
}
