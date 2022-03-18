import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class MapChoiceListener implements ActionListener {
	private Initializer init ;
	private ServerCreationFrame scf;
	private JButton associatedButton ;
	
	public MapChoiceListener(Initializer init, JButton associatedButton){
		this.init=init ;
		this.associatedButton=associatedButton ;
	}

	public MapChoiceListener(ServerCreationFrame scf, JButton associatedButton) {
		this.scf = scf;
		this.associatedButton=associatedButton;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(init!=null) {
			init.setChosenMap(associatedButton.getText()) ;
		}
		if (scf!=null) {
			scf.setChosenMap(associatedButton.getText());
		}
	}
}
