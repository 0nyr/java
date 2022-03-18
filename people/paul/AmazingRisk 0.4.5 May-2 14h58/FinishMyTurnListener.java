import java.awt.event.*;

//Cet écouteur sert à effectuer les opérations nécessaires lorsque le joueur courant a fini son tour (et donc qu'il a cliqué sur Finish).

public class FinishMyTurnListener implements ActionListener {
	private Sync sync ;
	
	public FinishMyTurnListener(Sync sync){
		this.sync=sync ;
	}

	public void actionPerformed(ActionEvent e) {
		sync.enterTheGameLoopAgain() ;
	}
	
	
}
