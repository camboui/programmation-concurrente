package jus.poc.prodcons.v4;
import jus.poc.prodcons.*;

 
public class MessageX implements Message {

	int idProd; // Permet de stocker qui à produit ce message
	int messNum;
	int nbMessage;
	Semaphore mutexMulti;
	
	public MessageX(int idProd, int messNum, int nbMessage){
		this.idProd = idProd;
		this.messNum = messNum;
		this.nbMessage = nbMessage;
		this.mutexMulti = new Semaphore(1);
		System.out.println("NbExemplaire = "+this.nbMessage);
	}
	
	/*public int nbExemplaire(){
		return nbMessage;
	}*/
	
	public String toString(){
		return "produit " + idProd + " message " + messNum;
	}
	
}
