package jus.poc.prodcons.v2;
import jus.poc.prodcons.*;

public class Producteur extends Acteur implements _Producteur {

	int nbMessProd; // nombre de messages qui doivent êtres produits
	int currentNbMessProd; // nombre de message(s) actuellement produit(s)
	Aleatoire nextProdTimer; // temps avant à la prochaine production
	ProdCons data; // moyen de comunication avec les consommateurs
	 
	protected Producteur(Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement, ProdCons data,int toProduce)
			throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);

		currentNbMessProd=0;
		nextProdTimer=new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.data=data;
		nbMessProd=toProduce;
	}

	@Override
	/*
	 * @return : nombre de message qu'il reste à écrire
	 */
	public int nombreDeMessages() {
		return nbMessProd-currentNbMessProd;
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
			try {
				data.put(this, new MessageX(this.identification(),currentNbMessProd)); // met son nom dans le message
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
