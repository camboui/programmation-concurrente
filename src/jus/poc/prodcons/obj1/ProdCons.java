package jus.poc.prodcons.obj1;
import jus.poc.prodcons.*;

public class ProdCons implements Tampon {

	int taille=0,nbPlein=0;
	int in=0,out=0;
	Message buffer[];
	
	
	public ProdCons(int taille){
		this.taille=taille;
		buffer = new Message[taille];
	}
	
	@Override
	public int enAttente() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public synchronized Message get(_Consommateur arg0) throws Exception,
			InterruptedException {
		while(nbPlein==0)
		{
		 wait();
		}
		Message r = buffer[out];
		out=(out+1)%taille;
		nbPlein--;
		notifyAll();
		return r;
	}
	

	@Override
	public synchronized void put(_Producteur arg0, Message arg1) throws Exception,
			InterruptedException {
		while(nbPlein>=taille)
		{
			wait();
		}
		buffer[in]=arg1;
		in=(in+1)%taille;
		nbPlein++;
		notifyAll();
		
	}

	@Override
	public int taille() {
		return taille;
	}

}
