package jus.poc.prodcons.obj1;
import jus.poc.prodcons.*;

public class Consommateur extends Acteur implements _Consommateur {

	int currentNbMessLect; // nombre de message(s) actuellement lu(s)
	Aleatoire nextLectTimer; // temps avant la prochaine lecture
	ProdCons data;
	
	protected Consommateur(int type, Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement,ProdCons data)
			throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		currentNbMessLect = 0; 
		nextLectTimer = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.data = data;

	}

	public void run() {
		
		//Le consommateur ne s'arrête jamais de consommer
		while(true)
		{
			//Le consommateur ne consomme pas pendant un temps aléatoire
			try {
				sleep(nextLectTimer.next());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//Il consomme
			currentNbMessLect++;
			MessageX m;
			try {
				m = data.get(this);
				System.out.println(m.toString()+" lu par "+this.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public int nombreDeMessages() {
		return currentNbMessLect;
	}

	

}
