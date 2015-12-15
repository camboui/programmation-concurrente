package jus.poc.prodcons.v5;
import jus.poc.prodcons.*;
import java.util.concurrent.locks.*;

public class ProdCons implements Tampon {

	int taille; // taille du buffer
	int nbPlein; // combien de cases sont pleines
	int in, out; // ou doit être mis/lu le prochain message
	MessageX[] buffer; // le buffer en lui même
	Observateur observateur;
	boolean inhiber;
	

	final Lock lock = new ReentrantLock();// On créer un lock
	final Condition notFull  = lock.newCondition(); // condition lock notFull
	final Condition notEmpty = lock.newCondition(); // condition lock notEmpty

	   
	public ProdCons(int taille,Observateur observateur,boolean inhiber){
		this.nbPlein = 0;
		this.in = 0;
		this.out = 0;
		this.taille = taille;
		this.inhiber=inhiber;
		this.observateur=observateur;
		buffer = new MessageX[taille];
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
	
	     lock.lock(); // On assure l'exclusion mutuelle
	     try {
	       while (nbPlein == 0){
	    	   notEmpty.await();} // attend que le buffer ne soit plus vide

			MessageX r = buffer[out]; // on recupère le bon message
			if(!inhiber){
				System.out.println("Consommateur " +  arg0.identification() + " : "+ r.toString());}
			observateur.retraitMessage(arg0, r);
			out = (out+1)%taille; // ou calcule ou sera le prochain message à lire
			nbPlein--; // on elève une case du nombre de cases pleines
	       
			notFull.signal();  // Envoi d'un signal pour dire que le buffer n'est pas plein
			return  r; // on retourne le message
	     } finally {
	       lock.unlock(); //Quand on est plus dans la SC, on dévérouille son accès
	     }
	}
	

	@Override
	public void put(_Producteur arg0, Message m) throws Exception,InterruptedException {
		
	     lock.lock();  // On assure l'exclusion mutuelle
	     try {
	        while (nbPlein == taille){
	    	   notFull.await();} // attend que le buffer ne soit plus plein

			buffer[in] = (MessageX) m; // on charge le message dans le buffer
			if(!inhiber){
				System.out.println("Producteur "+arg0.identification() + " : " + m.toString());}
			observateur.depotMessage(arg0, m);
			in = (in+1)%taille; // on calcul la prochaine position du prochain message
			nbPlein++; // on dit qu'un message de plus est à lire
			
	       notEmpty.signal(); //Envoi un signal pour dire que le buffer n'est pas plein
	     } finally {
	       lock.unlock(); //Quand on est plus dans la SC, on dévérouille son accès
	     }
	}

	@Override
	public int taille() {
		return taille;
	}

}
