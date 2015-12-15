package jus.poc.prodcons.obj1;
import jus.poc.prodcons.*;

public class Consommateur extends Acteur implements _Consommateur {

	int currentNbMessLect; // nombre de message(s) actuellement lu(s)
	Aleatoire nextLectTimer; // temps avant la prochaine lecture
	ProdCons data; // lien avec les producteurs
	
	protected Consommateur(Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement,ProdCons data)
			throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		currentNbMessLect = 0; 
		nextLectTimer = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.data = data;
 
	}

	public void run() {
		
		//Le consommateur ne s'arrête jamais de consommer
		
		//for(int currentNbMessLect=0;currentNbMessLect < toConsume; currentNbMessLect ++)
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
				System.out.println(this.getName() + " lit le message de "+ m.toString());
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
