package jus.poc.prodcons.v5;
import jus.poc.prodcons.*;

 
public class MessageX implements Message {

	int idProd; // Permet de stocker qui à produit ce message
	int messNum;
	
	public MessageX(int idProd,int messNum ){
		this.idProd = idProd;
		this.messNum = messNum;
	}
	
	public String toString(){
		return "produit " + idProd + " message " + messNum;
	}
	
}
