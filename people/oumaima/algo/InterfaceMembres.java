import java.awt.* ;
import javax.swing.* ;
import java.util.*;

public class InterfaceMembres extends JFrame {


//liste de membres
private LinkedList <Membre> MembreList ;


private MembresDeLAsso mba;
//attributs de la classe 
	//etiquettes 
 
 private JLabel nom ;
 private JLabel prenom ;
 private JLabel dateDeNaissance ;
 private JLabel mail ;
 private JLabel id ;
 private JLabel ajoutMembre ;
 private JLabel supprimerMembre ;
 private JLabel recherche ;
		
 
	//champs de saisie
 private JTextField champsDeSaisieNom ;
 private JTextField champsDeSaisiePrenom ;
 private JTextField champsDeSaisieDateDeNaissance ;
 private JTextField champsDeSaisieMail ;
 private JTextField champsDeSaisieId ;
 private JTextField rechercheNomEntree ;
 private JTextField rechercheNomSortie ;
 

	//boutons 
private JButton validerAjout ;
private JButton validerRecherche ;
private JButton validerSuppression ;

    //affichage 
 private JTextArea aireResultat ;
 
	//contructeur 
	public InterfaceMembres(MembresDeLAsso mba){
		super("Gestionnaire Membre");
		setSize(1200 , 4000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.mba = mba;
		this.MembreList= mba.getMembres();
		
	//instanciation des widegts de la fenetre
	nom = new JLabel ("Nom :");
	prenom = new JLabel ("Prenom:");
	dateDeNaissance = new JLabel ("Date de Naissance:");
	mail = new JLabel ("mail : ");
	id = new JLabel ("Identifiant : ");
	ajoutMembre = new JLabel ("Ajouter un membre");
	supprimerMembre = new JLabel ("Supprimer un membre");
	recherche = new JLabel("Rechercher");
	
	champsDeSaisieNom = new JTextField("",10);
	champsDeSaisiePrenom = new JTextField("",10);
	champsDeSaisieDateDeNaissance = new JTextField("",10);
	champsDeSaisieMail = new JTextField("",20);
	champsDeSaisieId = new JTextField("",10);
	rechercheNomEntree = new JTextField("",25);
	rechercheNomSortie = new JTextField("",25);
	
	aireResultat = new JTextArea(40,10);
	aireResultat.setEditable(false);
	
	validerAjout = new JButton ("Valider");
	validerRecherche = new JButton ("Valider");
	validerSuppression = new JButton ("Valider");
	
	
	
	
	//organisation structurelle 
		//declarer le panel d'entrée 
	JPanel panelEntree = new JPanel();
	
		//couleur 
	panelEntree.setBackground(Color.yellow);
		
		//ajout des widgets 
	
	panelEntree.add(nom);
	panelEntree.add(champsDeSaisieNom);
	panelEntree.add(prenom);
	panelEntree.add(champsDeSaisiePrenom);
	panelEntree.add(dateDeNaissance);
	panelEntree.add(champsDeSaisieDateDeNaissance);
	panelEntree.add(mail);
	panelEntree.add(champsDeSaisieMail);
	panelEntree.add(id);
	panelEntree.add(champsDeSaisieId);
	panelEntree.add(validerAjout); 
	/*panelEntree.add(recherche);
	panelEntree.add(rechercheNomEntree);
	panelEntree.add(validerRecherche);*/
	 
	//panel centre 
	JPanel panelCentre = new JPanel() ;
	panelCentre.setBackground(Color.white);

	aireResultat.setText(this.afficherListeMembres());
	

	panelCentre.add(aireResultat);
	
	
		//déclarer le panel de sortie
	JPanel panelSortie = new JPanel () ; 
	
		//couleur
	panelSortie.setBackground(Color.green);
	
		//ajout des widgets
	panelSortie.add(supprimerMembre);
	panelSortie.add(rechercheNomSortie);
	panelSortie.add(validerSuppression);
	panelSortie.add(recherche);
	panelSortie.add(rechercheNomEntree);
	panelSortie.add(validerRecherche);
	
	//déclarer panel principal
	JPanel panelPrincipal = new JPanel(new BorderLayout());
	
	// ajout des autres panels dans le panel principal
	panelPrincipal.add(panelEntree , BorderLayout.NORTH );
	panelPrincipal.add(panelCentre , BorderLayout.CENTER );
	panelPrincipal.add(panelSortie , BorderLayout.SOUTH);
	
	//ajout du panel principal 
	add(panelPrincipal);
	
	//liaison boutons-ecouteurs
	validerAjout.addActionListener(new AjoutMembre(this));
	validerRecherche.addActionListener(new RechercherMembre(this));
	/*validerSuppression
	*/
	
	setVisible(true);
	
	}
	
	// méthodes
	//rechercher un membre et l'afficher 
	//afficher les membres de l'assos 
	
	public String afficherListeMembres(){
	String text = " Les membres de l'association sont : \n" ;
	for(int i=0 ; i< MembreList.size() ; i++){
            Membre  m = MembreList.get(i);
            text = text + "  * "+m.toString()+ "\n";
        }
     
        aireResultat.setText(text); 
        return text;
      }
	
	//getters
	public int getId(){
		return Integer.parseInt(id.getText());
	}
	
	//ajouter un membre
	public void ajouterMembre(){
		String nom = this.champsDeSaisieNom.getText();
		String prenom = this.champsDeSaisiePrenom.getText();
		String dateNaissance = this.champsDeSaisieDateDeNaissance.getText();
		String mail = this.champsDeSaisieMail.getText();
		int id = this.getId();
		
		Membre m = new Membre (nom , prenom, dateNaissance , mail , id); 
		MembreList.add(m);
		
	 } 	 
	 
	 
	public String rechercher(String text){
		
		Membre m = new Membre("inconnu", "inconnu", "inconnu", "inconnu", 0);
		for(int i =0; i < MembreList.size(); i++){
			if(text.equals((MembreList.get(i)).getNom())){
				m = MembreList.get(i);
			}
			else if(text.equals((MembreList.get(i)).getPrenom())){
				m = MembreList.get(i);
			}
			else if(text.equals((MembreList.get(i)).getDateNaissance())){
				m = MembreList.get(i);
			}
			else if(text.equals((MembreList.get(i)).getMail())){
				m = MembreList.get(i);
			}
			else if(text.equals((MembreList.get(i)).getId())){
				m = MembreList.get(i);
			
			}
	
		}
		return m.toString();
	}
	 
	
	 // rechercher membre
	 public String rechercherMembre(){
		String text = rechercher(rechercheNomEntree.getText());
		aireResultat.setText(text);
		
		return text;

	 }
	 	 
	// recherche un membre et le supprimer 
	public void supprimerMembre(Membre m){ 
		MembreList.remove(m);
	}
	
	
	
	}
	

		
		
		
		
	
	

	
 
 
 
 

