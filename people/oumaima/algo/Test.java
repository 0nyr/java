
import java.util.*;


public class Test {
	public static void main( String [] args){


	//ajout personne
	Personne p1 = new Personne ("Benlemlih" , "Oumaima", "12/06/2000", "oumaimabenelemlih@outlook.fr");
	Membre p2 = new Membre ("Benlemlih" , "Oumaima", "12/06/2000", "oumaimabenelemlih@outlook.fr", 154630);
	Personne p3 = new Bureau ("Benlemlih" , "Oumaima", "12/06/2000", "oumaimabenelemlih@outlook.fr",154630,"presidente");
	Membre p4 = new Membre ("Ben" , "Ouma", "12/12/2000", "oumaimabenelemlih@outlook.fr", 124579);


		
	System.out.println(p1.toString());
	System.out.println(p2.toString());
	System.out.println(p3.toString());
	System.out.println(p2.equals(p3));
	
	//création d'une liste 
	
	LinkedList<Membre> ListeMembres = new LinkedList<Membre>();
	
	

	// creation d'un budget
		Budget b = new Budget();
	
	// creation mba
		MembresDeLAsso mbA = new MembresDeLAsso(b);
	
		
		mbA.ajouterMembre(p4);
	//création d'une interface 
	InterfaceMembres gestionnaireMembre = new InterfaceMembres(mbA);
	
	
	gestionnaireMembre.afficherListeMembres();
	
	
	
	


	}
}
