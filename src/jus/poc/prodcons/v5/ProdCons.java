package jus.poc.prodcons.v5;
import jus.poc.prodcons.*;
import java.util.concurrent.locks.*;

public class ProdCons implements Tampon {

	int taille; // taille du buffer
	int nbPlein; // combien de cases sont pleines
	int in, out; // ou doit être mis/lu le prochain message
	MessageX[] buffer; // le buffer en lui même
	Semaphore sCons, sProd,sMutex; // Les sémaphores producteur et consommateur
	Observateur observateur;
	boolean inhiber;
	
	final Lock lock = new ReentrantLock();
	final Condition notFull  = lock.newCondition(); 
	final Condition notEmpty = lock.newCondition(); 

	   
	public ProdCons(int taille,Observateur observateur,boolean inhiber){
		this.nbPlein = 0;
		this.in = 0;
		this.out = 0;
		this.taille = taille;
		this.inhiber=inhiber;
		this.observateur=observateur;
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
	
	     lock.lock();
	     try {
	       while (nbPlein == 0){
	    	   notEmpty.await();}

			MessageX r = buffer[out]; // on recupère le bon message
			if(!inhiber){
				System.out.println("Consommateur " +  arg0.identification() + " : "+ r.toString());}
			observateur.retraitMessage(arg0, r);
			out = (out+1)%taille; // ou calcule ou sera le prochain message à lire
			nbPlein--; // on elève une case du nombre de cases pleines
	       
			notFull.signal();
			return  r; // on retourne le message
	     } finally {
	       lock.unlock();
	     }
	}
	

	@Override
	public void put(_Producteur arg0, Message m) throws Exception,InterruptedException {
		
	     lock.lock();
	     try {
	        while (nbPlein == taille){
	    	   notFull.await();}

			buffer[in] = (MessageX) m; // on charge le message dans le buffer
			if(!inhiber){
				System.out.println("Producteur "+arg0.identification() + " : " + m.toString());}
			observateur.depotMessage(arg0, m);
			in = (in+1)%taille; // on calcul la prochaine position du prochain message
			nbPlein++; // on dit qu'un message de plus est à lire
			
	       notEmpty.signal();
	     } finally {
	       lock.unlock();
	     }
	}

	@Override
	public int taille() {
		return taille;
	}

}
