import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

//THIS LISTENER CAN BE APPLIED TO MANY WINDOWS BECAUSE IT IMPLICATES THE dispose() METHOD.

public class CloseListener implements ActionListener {
	protected JFrame jf ;
	 
	public CloseListener(JFrame jf){
		this.jf=jf ;
	}

	public void actionPerformed(ActionEvent e) {
		jf.dispose();
	}
	
	
}
