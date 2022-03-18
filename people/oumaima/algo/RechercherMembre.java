import java.awt.event.*;

public class RechercherMembre implements ActionListener {

	private InterfaceMembres gestionnaireMembre;
	
	public RechercherMembre (InterfaceMembres gestionnaireMembre){
		this.gestionnaireMembre = gestionnaireMembre;
		
	}
	public void actionPerformed(ActionEvent e){
		
	 gestionnaireMembre.rechercherMembre();
	}
	
}
