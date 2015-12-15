package jus.poc.prodcons.v4;
import jus.poc.prodcons.*;

public class Producteur extends Acteur implements _Producteur {

	int nbMessProd; // nombre de messages qui doivent êtres produits
	int currentNbMessProd; // nombre de message(s) actuellement produit(s)
	Aleatoire nextProdTimer; // temps avant à la prochaine production
	ProdCons data; // moyen de comunication avec les consommateurs
	Observateur observateur;
	int delaisProd;
	Message mess;
	int nbExemplaire; // nombre d'exemplaire à faire de chaque message
	
	protected Producteur(Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement, 
			ProdCons data, int toProduce, int toDuplique)
			throws ControlException {
		
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.observateur = observateur;
		this.currentNbMessProd = 0;
		this.nextProdTimer = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.data = data;
		this.nbMessProd = toProduce;
		this.nbExemplaire = toDuplique;
	}

	@Override
	/*
	 * @return : nombre de message qu'il reste à écrire
	 */
	public int nombreDeMessages() {
		return (nbMessProd*nbExemplaire)-currentNbMessProd;
	}
	
	public void run()
	{
		//on produit nbMessProd messages
		for(currentNbMessProd=0 ; currentNbMessProd<nbMessProd ; currentNbMessProd++)
		{
			// le producteur ne produit pas pendant un temps aléatoire
			try {
				delaisProd=nextProdTimer.next();
				sleep(delaisProd);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Puis on produit
			try {
				mess = new MessageX(this.identification(), currentNbMessProd, nbExemplaire);
				observateur.productionMessage(this, mess, delaisProd);
				data.put(this, mess); // met son nom dans le message
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
