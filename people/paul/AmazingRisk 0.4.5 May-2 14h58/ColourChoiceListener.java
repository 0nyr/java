
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Collections;


public class ColourChoiceListener implements ActionListener {
	private Initializer init ;
	private PlayerServerMenu psm;
	private JComboBox mycb ;
	
	public ColourChoiceListener(Initializer init, JComboBox mycb){
		this.init=init ;
		this.mycb=mycb ;
	}

	public ColourChoiceListener(PlayerServerMenu psm, JComboBox mycb) {
		this.psm=psm;
		this.mycb=mycb;
	}

	public void actionPerformed(ActionEvent e) {
		if(init!=null) {
			init.setComboBoxColour(mycb) ;
		}
		else if(psm!=null) {
			psm.setClientColour();
		}
	}
	
	
}
