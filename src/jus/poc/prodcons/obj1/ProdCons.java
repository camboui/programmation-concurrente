package jus.poc.prodcons.obj1;
import jus.poc.prodcons.*;

public class ProdCons implements Tampon {

	int taille, nbPlein;
	int in, out;
	Message[] buffer;
	
	public ProdCons(int taille){
		this.nbPlein = 0;
		this.in = 0;
		this.out = 0;
		this.taille = taille;
		buffer = new Message[taille];
	}
	
	@Override
	/*
	 * @return : nombre de message en attente de lecteurs
	 */
	public int enAttente() {
		return (in-out)%taille;
	}

	@Override
	public synchronized Message get(_Consommateur arg0) throws Exception, InterruptedException {
		while(nbPlein == 0){
			wait();
		}
		Message r = buffer[out];
		out = (out+1)%taille;
		nbPlein--;
		notifyAll();
		return r;
	}
	

	@Override
	public synchronized void put(_Producteur arg0, Message m) throws Exception,InterruptedException {
		while(nbPlein >= taille){
			wait();
		}
		buffer[in] = m;
		in = (in+1)%taille;
		nbPlein++;
		notifyAll();
		
	}

	@Override
	public int taille() {
		return taille;
	}

}
