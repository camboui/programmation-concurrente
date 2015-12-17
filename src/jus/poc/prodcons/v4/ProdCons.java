package jus.poc.prodcons.v4;
import jus.poc.prodcons.*;

public class ProdCons implements Tampon {

	int taille; // taille du buffer
	int nbPlein; // combien de cases sont pleines
	int in, out; // ou doit être mis/lu le prochain message
	MessageX[] buffer; // le buffer en lui même
	Semaphore sCons, sProd, sMutex, sExemplaires; // Les sémaphores producteur et consommateur
	Observateur observateur;
	boolean inhiber;
	
	public ProdCons(int taille, Observateur observateur, boolean inhiber){
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
		this.sExemplaires = new Semaphore(0);
	}
	
	@Override
	/*
	 * @return : nombre de message en attente de lecteurs
	 */
	public synchronized int enAttente() {
		int nbAttente = 0;
		for(int i=(out%taille) ; i<(in%taille + taille) ; i++){
			nbAttente += buffer[i%taille].nbMessage;
		}
		return nbAttente;
	}

	@Override
	public MessageX get(_Consommateur arg0) throws Exception, InterruptedException {
	
		sCons.P(); // S'il y a une ressource, elle est lue. Sinon, Le consommateur est mis en pause.
		// Si quelqu'un accède déjà à la SC, celui qui arrive ici est mis en pause.
		sMutex.P(); //On protège les données partagées
		MessageX r = buffer[out]; // on recupère le bon message
		if(!inhiber){
			System.out.println("Consommateur " +  arg0.identification() + " : "+ r.toString());
		}
		observateur.retraitMessage(arg0, r);
		if(r.nbMessage == 1){
			r.nbMessage--;
			out = (out+1)%taille; // ou calcule ou sera le prochain message à lire
			nbPlein--; // on elève une case du nombre de cases pleines
			sProd.V(); //On réveille un producteur
			sExemplaires.V(); // reveil de celui qui a créé le message
		}else{
			r.nbMessage--;
			sCons.V();
		}
		sMutex.V(); // On redonne l'accès aux données partagées
		
		return  r; // on retourne le message
	}
	

	@Override
	public void put(_Producteur arg0, Message m) throws Exception,InterruptedException {
		sProd.P(); // buffer plein ! 
		sMutex.P(); //On protège les données partagées 
		buffer[in] = (MessageX) m; // on charge le message dans le buffer
		if(!inhiber){
			System.out.println("Producteur "+arg0.identification() + " : " + m.toString());}
		observateur.depotMessage(arg0, m);
		in = (in+1)%taille; // on calcul la prochaine position du prochain message
		nbPlein++; // on dit qu'un message de plus est à lire
		sMutex.V();// On redonne l'accès aux données partagées
		sCons.V(); //ajout de +1 dans un consomateur possible
		sExemplaires.P(); // bloquage du producteur
		}

	@Override
	public int taille() {
		return taille;
	}

}
