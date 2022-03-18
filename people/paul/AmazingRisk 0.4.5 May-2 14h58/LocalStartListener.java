import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;


public class LocalStartListener implements ActionListener {
	private Initializer init ;
	private ServerCreationFrame scf;
	
	public LocalStartListener(Initializer init){
		this.init=init ;
	}

	public LocalStartListener(ServerCreationFrame scf) {this.scf=scf;}

	public void actionPerformed(ActionEvent e) {
		if(init!=null) {
			try {
				init.start() ;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		if(scf!=null) {
			scf.createServerGame();
		}
	}
	
	
}
