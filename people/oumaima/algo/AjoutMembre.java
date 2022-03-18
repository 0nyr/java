import java.awt.event.*;

public class AjoutMembre implements ActionListener{
	
	private InterfaceMembres gestionnaireMembre;
	private MembresDeLAsso mba ;
	private Membre m ;
	
	public AjoutMembre ( InterfaceMembres gestionnaireMembre){
		this.gestionnaireMembre = gestionnaireMembre;
		
		
	}
	public void actionPerformed(ActionEvent e){
		
		mba.ajouterMembre(m);
		gestionnaireMembre.afficherListeMembres();
	}
	
}
