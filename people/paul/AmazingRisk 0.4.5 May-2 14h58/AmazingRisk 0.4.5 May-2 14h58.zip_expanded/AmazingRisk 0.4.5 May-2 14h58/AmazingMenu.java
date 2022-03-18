import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AmazingMenu extends JFrame{
	
	private JPanel choicePanel ;
	private JButton localgameBtn,onlinegameBtn,rulesBtn ;

	public AmazingMenu() {
		super(TitleOf.AmazingMenu) ;
		setSize(500,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(false);
		setResizable(false);
		setLocationRelativeTo(null);
		
		this.setJMenuBar( this.createMenuBar() );
		
		choicePanel = new JPanel(new GridLayout(2,2)) ;

		localgameBtn = new JButton("<html><b>Start a local game</b></html>",new ImageIcon("icons/local-game.png")) ;
		onlinegameBtn = new JButton("<html><b>Start a online game</b></html>",new ImageIcon("icons/online-game.png")) ;
		rulesBtn = new JButton("<html><b>Learn the rules of the game</b></html>",new ImageIcon("icons/rules.png")) ;
		
		localgameBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		localgameBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		localgameBtn.addActionListener(new CloseListener(this) {
			public void actionPerformed(ActionEvent e) {
				new Initializer() ;
				jf.dispose();
			}
		});

		onlinegameBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		onlinegameBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		onlinegameBtn.addActionListener(new CloseListener(this) {
			public void actionPerformed(ActionEvent e) {
				new OnlineFrame() ;
				jf.dispose() ;
			}
		}); 

		rulesBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		rulesBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		//actionListener du rulesBtn à ajouter !
		
		choicePanel.add(localgameBtn) ;
		choicePanel.add(onlinegameBtn) ;
		choicePanel.add(rulesBtn) ;
		this.add(choicePanel) ;
		
		validate();
		repaint();
	
	
	}
	
	private JMenuBar createMenuBar() { //METHODE DE CREATION D'UN MENU EN HAUT QUI PERMET D'AVOIR ACCES A DES OPTIONS.
		
		/**CE MENU EST ENCORE INCOMPLET ET NE SERT POUR LE MOMENT A RIEN - C'est juste un "embryon" de MENU pour aider à faire le vrai*/

        // La barre de menu à proprement parler
        JMenuBar menuBar = new JMenuBar();

        // Définition du menu déroulant "File" et de son contenu
        JMenu mnuFile = new JMenu( "Game" );
        mnuFile.setMnemonic( 'G' );

        JMenuItem mnuNewFile = new JMenuItem( "New Game" ); //Ajout d'un élément au menu
        mnuNewFile.setIcon( new ImageIcon( "icons/new.png" ) );
        mnuNewFile.setMnemonic( 'N' );
        /*mnuNewFile.addActionListener( this::mnuNewListener );*/
        mnuNewFile.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK) ); //Accélérateur de clavier pour accéder à l'option
        mnuFile.add(mnuNewFile);

        mnuFile.addSeparator();

        JMenuItem mnuOpenFile = new JMenuItem( "Open a game" );
        mnuOpenFile.setIcon( new ImageIcon( "icons/open.png" ) );
        mnuOpenFile.setMnemonic('O');
        mnuOpenFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK) );
        mnuFile.add(mnuOpenFile);

        JMenuItem mnuSaveFile = new JMenuItem( "Save the game" );
        mnuSaveFile.setIcon( new ImageIcon( "icons/save.png" ) );
        mnuSaveFile.setMnemonic( 'S' );
        mnuSaveFile.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK) );
        mnuFile.add(mnuSaveFile);

        mnuFile.addSeparator();

        JMenuItem mnuExit = new JMenuItem( "Exit" );
        mnuExit.setIcon( new ImageIcon( "icons/exit.png" ) );
        mnuExit.setMnemonic( 'x' );
        mnuExit.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK) );
        mnuFile.add(mnuExit);
        
        menuBar.add(mnuFile);
        
        // Définition du menu déroulant "Help" et de son contenu
        JMenu mnuHelp = new JMenu( "Help" );
        mnuHelp.setMnemonic( 'H' );
        
        menuBar.add( mnuHelp );
        
        return menuBar;
    }
    
    /*public void mnuNewListener( ActionEvent event ) { //méthode servant de listener.
        JOptionPane.showMessageDialog( this, "Button clicked !" );
    }*/
	
	
	/*public static void main(String[] args) throws Exception{
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		AmazingMenu am = new AmazingMenu() ;
	}*/
	
}
