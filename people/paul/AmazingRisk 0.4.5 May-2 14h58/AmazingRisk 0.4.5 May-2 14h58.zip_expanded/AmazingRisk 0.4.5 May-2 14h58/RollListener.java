import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;


public class RollListener implements ActionListener {
	private RollingFrame rf ;
	 
	public RollListener(RollingFrame rf){
		this.rf=rf ;
	}

	public void actionPerformed(ActionEvent e) {
		rf.roll() ;
	}
	
	
}
