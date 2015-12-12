package jus.poc.prodcons.obj1;
import jus.poc.prodcons.*;

public class Producteur extends Acteur implements _Producteur {

	int nbMessProd; // nombre de messages qui doivent êtres produits
	int currentNbMessProd=0; // nombre de message(s) actuellement produit(s)
	Aleatoire nextProdTimer; // temps avant à la prochaine production
	
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
