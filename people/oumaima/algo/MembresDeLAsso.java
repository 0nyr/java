import java.util.*;
public class MembresDeLAsso {
	
	private double prixAdhesion=20;
	private LinkedList <Membre> membresAsso;
	private Budget b;
	
	public MembresDeLAsso(Budget b){
		this.b=b;
		membresAsso = new LinkedList<Membre>();
	}
	
	//getter
	public LinkedList <Membre> getMembres() { return membresAsso;}
	
	//methodes pour ajouter/supprimer un membre 
	public void ajouterMembre(Membre m){
		if (membresAsso.contains(m)){
            System.out.println("Cette personne fait deja partie de l'association");
        }else {
            membresAsso.add(m);
            b.entrerArgent(prixAdhesion);
		}
	}
	
	public void supprimerMembre (Membre m){
			if (!membresAsso.contains(m)){
            System.out.println("Cette personne ne fait pas partie de l'association");
        }else {
            membresAsso.remove(m);
		}
	}
	
	public String affichage(){
		String res="+ "+prixAdhesion*membresAsso.size()+" : adhesion des "+membresAsso.size()+" membres \n";
		return res;
	}
	
}

