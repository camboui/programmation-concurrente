package jus.poc.prodcons.obj1;
import jus.poc.prodcons.*;

public class Consommateur extends Acteur implements _Consommateur {

	int nbMessLect; // nombre de messages qui doivent êtres lus
	int currentNbMessLect=0; // nombre de message(s) actuellement lu(s)
	Aleatoire nextLectTimer; // temps avant la prochaine lecture
	
	protected Consommateur(int type, Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement)
			throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
	}

	public void run() {
		
	}

	@Override
	public int nombreDeMessages() {
		return currentNbMessLect;
	}

	

}
