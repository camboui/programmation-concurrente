package jus.poc.prodcons.v2;
import jus.poc.prodcons.*;

public class ProdCons implements Tampon {

	int taille; // taille du buffer
	int nbPlein; // combien de cases sont pleines
	int in, out; // ou doit être mis/lu le prochain message
	MessageX[] buffer; // le buffer en lui même
	
	public ProdCons(int taille){
		this.nbPlein = 0;
		this.in = 0;
		this.out = 0;
		this.taille = taille;
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
	public synchronized MessageX get(_Consommateur arg0) throws Exception, InterruptedException {
		while(nbPlein <= 0){
			wait(); // on attend le prochain message s'il n'y a plus dans le buffer
		} 
		MessageX r = buffer[out]; // on recupère le bon message
		out = (out+1)%taille; // ou calcule ou sera le prochain message à lire
		nbPlein--; // on elève une case du nombre de cases pleines
		notifyAll(); // on reveille tout le monde histoire de voir si des producteurs ne pourraient pas mettre de nouveaux messages
		return  r; // on retourne le message
	}
	

	@Override
	public synchronized void put(_Producteur arg0, Message m) throws Exception,InterruptedException {
		while(nbPlein >= taille){
			wait(); // on attent tant que le buffer est plein
		}
		buffer[in] = (MessageX) m; // on charge le message dans le buffer
		in = (in+1)%taille; // on calcul la prochaine position du prochain message
		nbPlein++; // on dit qu'un message de plus est à lire
		notifyAll(); // on reveille tous les autres, histoire que quelqu'un puisse lire ce message ou un autre, ou dépose un nouveau message après celui là
	}

	@Override
	public int taille() {
		return taille;
	}

}
