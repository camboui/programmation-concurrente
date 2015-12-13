package jus.poc.prodcons.obj1;
import jus.poc.prodcons.*;

public class Consommateur extends Acteur implements _Consommateur {

	int currentNbMessLect; // nombre de message(s) actuellement lu(s)
	Aleatoire nextLectTimer; // temps avant la prochaine lecture
	
	protected Consommateur(int type, Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement)
			throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		currentNbMessLect=0; 
		nextLectTimer=new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);

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
			
			//Il doit retirer = get, il faut un accès au buffer ?
		}
	}

	@Override
	public int nombreDeMessages() {
		return currentNbMessLect;
	}

	

}
