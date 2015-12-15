package jus.poc.prodcons.v2;
import jus.poc.prodcons.*;
import jus.poc.prodcons.v3.MessageX;

public class ProdCons implements Tampon {

	int taille; // taille du buffer
	int nbPlein; // combien de cases sont pleines
	int in, out; // ou doit être mis/lu le prochain message
	MessageX[] buffer; // le buffer en lui même
	Semaphore sCons, sProd,sMutex; // Les sémaphores producteur et consommateur
	boolean inhiber;
	
	public ProdCons(int taille,boolean inhiber){
		this.nbPlein = 0;
		this.in = 0;
		this.out = 0;
		this.taille = taille;
		this.inhiber=inhiber;
		buffer = new MessageX[taille];
		sProd = new Semaphore(taille);
		sCons = new Semaphore(0);
		sMutex = new Semaphore(1);
	}
	
	@Override
	/*
	 * @return : nombre de message en attente de lecteurs
	 */
	public int enAttente() {
		return (in-out)%taille;
	}

	@Override
	public MessageX get(_Consommateur arg0) throws Exception, InterruptedException {
		sCons.P(); // S'il y a une ressource, elle est lue. Sinon, Le consommateur est mis en pause.
		// Si quelqu'un accède déjà à la SC, celui qui arrive ici est mis en pause.
		sMutex.P(); //On protège les données partagées
		MessageX r = buffer[out]; // on recupère le bon message
		if(!inhiber){
			System.out.println("Consommateur " +  arg0.identification() + " : "+ r.toString());}
		out = (out+1)%taille; // ou calcule ou sera le prochain message à lire
		nbPlein--; // on elève une case du nombre de cases pleines
		sMutex.V(); // On redonne l'accès aux données partagées
		sProd.V(); //On réveille un producteur
		
		return  r; // on retourne le message
	}
	

	@Override
	public void put(_Producteur arg0, Message m) throws Exception,InterruptedException {
		
		sProd.P(); // On produit.Si tous les messages sont produits, on attend
		// Si quelqu'un accède déjà à la SC, celui qui arrive ici est mis en pause.
		sMutex.P(); //On protège les données partagées 
		buffer[in] = (MessageX) m; // on charge le message dans le buffer
		if(!inhiber){
			System.out.println("Producteur "+arg0.identification() + " : " + m.toString());}
		in = (in+1)%taille; // on calcul la prochaine position du prochain message
		nbPlein++; // on dit qu'un message de plus est à lire
		sMutex.V();// On redonne l'accès aux données partagées
		sCons.V();//On reveille un consommateur
	}

	@Override
	public int taille() {
		return taille;
	}

}
