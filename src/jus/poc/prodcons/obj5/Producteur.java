package jus.poc.prodcons.obj5;
import jus.poc.prodcons.*;

public class Producteur extends Acteur implements _Producteur {

	protected Producteur(int type, Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement)
			throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
