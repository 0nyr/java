import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class OnlineFrame extends JFrame{

	private boolean isServerFrameOpen = false ;

	private JPanel buttonsPanel ;
	private JButton createGameBtn, joinGameBtn ;
	

	public OnlineFrame() {
		super(TitleOf.OnlineFrame) ;
		setSize(500,400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);	
		
		buttonsPanel=new JPanel(new FlowLayout()) ;
		createGameBtn=new JButton("<html><b>Create a new game</b></html>",new ImageIcon("icons/creategame.png")) ;
		joinGameBtn=new JButton("<html><b>Join a game</b></html>",new ImageIcon("icons/joingame.png")) ;

		createGameBtn.addActionListener(new CloseListener(this) {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ServerCreationFrame() ;
				isServerFrameOpen = true ;
				jf.dispose();
			}
		});

		joinGameBtn.addActionListener(new CloseListener(this) {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ServerJoinFrame();
				isServerFrameOpen = true ;
				jf.dispose();
			}
		});

		buttonsPanel.add(createGameBtn) ;
		buttonsPanel.add(joinGameBtn) ;
		
		add(buttonsPanel, BorderLayout.NORTH) ;
	}
	
	public void dispose() {
		if(!isServerFrameOpen) {
			AmazingMenu am = new AmazingMenu() ;
			am.setVisible(true) ;
		}
		super.dispose() ;
	}
	
}
