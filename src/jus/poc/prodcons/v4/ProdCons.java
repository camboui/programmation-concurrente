package jus.poc.prodcons.v4;
import jus.poc.prodcons.*;

public class ProdCons implements Tampon {

	int taille; // taille du buffer
	int nbPlein; // combien de cases sont pleines
	int in, out; // ou doit être mis/lu le prochain message
	MessageX[] buffer; // le buffer en lui même
	Semaphore sCons, sProd,sMutex; // Les sémaphores producteur et consommateur
	Observateur observateur;
	boolean inhiber;
	
	public ProdCons(int taille,Observateur observateur, boolean inhiber){
		this.nbPlein = 0;
		this.in = 0;
		this.out = 0;
		this.taille = taille;
		this.inhiber = inhiber;
		this.observateur = observateur;
		this.buffer = new MessageX[taille];
		this.sProd = new Semaphore(taille);
		this.sCons = new Semaphore(0);
		this.sMutex = new Semaphore(1);
	}
	
	@Override
	/*
	 * @return : nombre de message en attente de lecteurs
	 */
	public int enAttente() {
		int nbAttente = 0;
		for(int i=(out%taille) ; i<(in%taille) ; i++){
			nbAttente += buffer[i].nbMessage;
			System.out.println("en attendte() : "+nbAttente);
		}
		return nbAttente;
		//return (in-out)%taille;
	}

	@Override
	public MessageX get(_Consommateur arg0) throws Exception, InterruptedException {
	
		//Si le buffer est vide, on attend jusqu'à ce qu'il contienne un message
		sCons.P();
		sMutex.P();
		this.enAttente();
		MessageX r = buffer[out]; // on recupère le bon message
		if(!inhiber){
			System.out.println("Consommateur " +  arg0.identification() + " : "+ r.toString());}
		observateur.retraitMessage(arg0, r);
		if(r.nbMessage == 1){
			System.out.println("fin des exemplaires");
			out = (out+1)%taille; // ou calcule ou sera le prochain message à lire
			nbPlein--; // on elève une case du nombre de cases pleines
			//r.mutexMulti.V();
		}else{
			System.out.println("il y a "+r.nbMessage+" il reste maintenant +"+(r.nbMessage-1));
			r.nbMessage--;
		}
		sMutex.V(); // on reveille tout le monde histoire de voir si des producteurs ne pourraient pas mettre de nouveaux messages
		//Réveille un thread
		sProd.V();
		
		return  r; // on retourne le message
	}
	

	@Override
	public void put(_Producteur arg0, Message m) throws Exception,InterruptedException {
		sProd.P();
		sMutex.P();
		buffer[in] = (MessageX) m; // on charge le message dans le buffer
		if(!inhiber){
			System.out.println("Producteur "+arg0.identification() + " : " + m.toString());}
		observateur.depotMessage(arg0, m);
		in = (in+1)%taille; // on calcul la prochaine position du prochain message
		nbPlein++; // on dit qu'un message de plus est à lire
		//((MessageX) m).mutexMulti.P(); // bloquage du producteur
		sMutex.V();
		sCons.V();// on reveille tous les autres, histoire que quelqu'un puisse lire ce message ou un autre, ou dépose un nouveau message après celui là
	}

	@Override
	public int taille() {
		return taille;
	}

}
