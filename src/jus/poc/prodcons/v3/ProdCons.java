package jus.poc.prodcons.v3;
import jus.poc.prodcons.*;

public class ProdCons implements Tampon {

	int taille; // taille du buffer
	int nbPlein; // combien de cases sont pleines
	int in, out; // ou doit être mis/lu le prochain message
	MessageX[] buffer; // le buffer en lui même
	Semaphore NotEmtpy, NotFull,Smutex; // Les sémaphores producteur et consommateur
	Observateur observateur;
	//Scons = NotEMpy
	//SProd = NotFull
	
	
	public ProdCons(int taille,Observateur observateur){
		this.nbPlein = 0;
		this.in = 0;
		this.out = 0;
		this.taille = taille;
		this.observateur=observateur;
		buffer = new MessageX[taille];
		NotFull = new Semaphore(taille);
		NotEmtpy = new Semaphore(0);
		Smutex = new Semaphore(1);
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
	
		//Si le buffer est vide, on attend jusqu'à ce qu'il contienne un message
		NotEmtpy.P();
		Smutex.P();

		MessageX r = buffer[out]; // on recupère le bon message
		System.out.println("Consommateur " +  arg0.identification() + " : "+ r.toString());
		observateur.retraitMessage(arg0, r);
		out = (out+1)%taille; // ou calcule ou sera le prochain message à lire
		nbPlein--; // on elève une case du nombre de cases pleines
		Smutex.V(); // on reveille tout le monde histoire de voir si des producteurs ne pourraient pas mettre de nouveaux messages
		//Réveille un thread
		NotFull.V();
		
		return  r; // on retourne le message
	}
	

	@Override
	public void put(_Producteur arg0, Message m) throws Exception,InterruptedException {
		NotFull.P();
		Smutex.P();
		buffer[in] = (MessageX) m; // on charge le message dans le buffer
		System.out.println("Producteur "+arg0.identification() + " : " + m.toString());
		observateur.depotMessage(arg0, m);
		in = (in+1)%taille; // on calcul la prochaine position du prochain message
		nbPlein++; // on dit qu'un message de plus est à lire
		Smutex.V();
		NotEmtpy.V();// on reveille tous les autres, histoire que quelqu'un puisse lire ce message ou un autre, ou dépose un nouveau message après celui là
	}

	@Override
	public int taille() {
		return taille;
	}

}
