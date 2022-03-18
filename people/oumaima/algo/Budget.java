public class Budget {
		
	private double solde=0;	
	
	public Budget(){ 
		//pas besoin d'initialiser le compte
	}
	
	
	//depenser, ajouterd de l'argent sur le compte
	public void sortirArgent(double prix){
		solde = solde-prix;
	}
	//depenser de l'argent du compte
	public void entrerArgent(double prix){
		solde = solde + prix;
	}
	//ajouter ou depenser selon si le prix est positif ou négatif
	public void setSolde(double prix){
		if (prix>0){
			solde = solde + prix;
		}else{
			solde = solde - prix;
		}
	}
	
	
	public double getSolde(){
		return solde;
	}
	
	public String toString(){
		return ("Le solde de l'association est de "+getSolde()+"euros.") ;
	}
	
}

