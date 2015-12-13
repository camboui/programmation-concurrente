package jus.poc.prodcons.obj1;
import jus.poc.prodcons.*;

public class Producteur extends Acteur implements _Producteur {

	int nbMessProd; // nombre de messages qui doivent êtres produits
	int currentNbMessProd; // nombre de message(s) actuellement produit(s)
	Aleatoire nextProdTimer; // temps avant à la prochaine production
	
	protected Producteur(int type, Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement)
			throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		currentNbMessProd=0;
		nextProdTimer=new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		//nbMessProd=new Aleatoire(type, deviation);
	}

	@Override
	public int nombreDeMessages() {
		return currentNbMessProd;
	}
	
	public void run()
	{
		//on produit nbMessProd messages
		for(currentNbMessProd=0;currentNbMessProd < nbMessProd;currentNbMessProd++)
		{
			// le producteur ne produit pas pendant un temps aléatoire
			try {
				sleep(nextProdTimer.next());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Puis on produit
			
			//Il doit déposer = put, il faut un accès au buffer ?
		}
		
	}
	

}
