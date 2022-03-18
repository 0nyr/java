import javax.swing.*;
import java.awt.*;
import java.awt.event.* ;
/*CETTE CLASSE PERMET DE GERER DES MENUS POPUPS, C'EST A DIRE DES ESPECES DE PANNEAUX QUI S'OUVRENT
 * LORSQUE JE CLIQUE SUR UN COMPONENT QUI LE DECLENCHE (un bouton souvent). ICI ELLE PERMET D'AFFICHER 
 * UN PANNEAU D'OPTIONS LORSQUE JE CLIQUE SUR "OPTIONS" (en bas de ma Frame de jeu). ELLE PERMET AUSSI
 * D'AFFICHER UN PANNEAU D'OPTIONS DES PAYS LORSQUE JE CLIQUE-DROIT SUR UN COUNTRYBUTTON*/

public class PopupMenu extends JPopupMenu{


	public PopupMenu(Country c){ //Constructeur qui admet un pays
		
		//DEBUT POPUP
		//countryMenu = new JPopupMenu();
		JPanel renamePanel = new JPanel(new FlowLayout()) ;
		JTextField renameField = new JTextField() ;
		JButton renameButton = new JButton("Rename") ;
		renameButton.setPreferredSize(new Dimension(100,33)) ;
		
		renameField.setPreferredSize(new Dimension(100,33)) ;
		renamePanel.add(renameField) ; renamePanel.add(renameButton) ;
		
		renameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.setName(renameField.getText()) ;
			}
		});
		//JMenuItem mnuUndo = new JMenuItem( "Undo" );
		//countryMenu.add(mnuUndo) ;
		add(renamePanel) ;
		//FIN POPUP
	}
	
	public PopupMenu(Sync sync, int uselessNb){ //Constructeur qui admet un pays
		add(new RankingPane(sync)) ;
		setPopupSize(new Dimension(400,300)) ;
	}
	/**AD important*/
	public PopupMenu(Sync sync){ //Constructeur qui admet un panel à afficher
		setBorderPainted(true) ;
		JPanel choicePanel = new JPanel(new GridLayout(2,2)) ;
		JButton statsBtn = new JButton("<html><b>Statistics</b></html>",new ImageIcon("icons/stats.png")) ;
		JButton rankingBtn = new JButton("<html><b>Ranking</b></html>",new ImageIcon("icons/podium.png")) ;
		JButton cardsBtn = new JButton("<html><b>My cards</b></html>",new ImageIcon("icons/cards.png")) ;
		JButton exitBtn = new JButton("<html><b>Exit the game</b></html>",new ImageIcon("icons/quit.png")) ;
		
		statsBtn.setVerticalTextPosition(SwingConstants.BOTTOM); //Permet d'afficher le text en bas de l'icône 
		statsBtn.setHorizontalTextPosition(SwingConstants.CENTER);//...et de l'afficher de façon centrée.
		statsBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(sync.dataAvaibility){
					new StatsFrame(sync) ;
				} else {
					String s="There is not enough datas to build a graph yet : please play for a few moment and try again !" ;
					JOptionPane.showMessageDialog(new JFrame(),s, "Not enough datas yet",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		exitBtn.setVerticalTextPosition(SwingConstants.BOTTOM); 
		exitBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		
		rankingBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		rankingBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		rankingBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(sync.dataAvaibility){
					JFrame RankingFrame = new JFrame(TitleOf.RankingFrame);
					RankingFrame.add(new RankingPane(sync)) ;
					RankingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					RankingFrame.setSize(new Dimension(900,500)) ;
					RankingFrame.validate() ;
					RankingFrame.repaint() ;
					RankingFrame.setVisible(true);
				} else {
					String s="There is not enough datas to build a graph yet : please play for a few moment and try again !" ;
					JOptionPane.showMessageDialog(new JFrame(),s,TitleOf.LackOfData,JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		cardsBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		cardsBtn.setHorizontalTextPosition(SwingConstants.CENTER); 
		
		choicePanel.add(statsBtn) ;
		choicePanel.add(rankingBtn) ;
		choicePanel.add(cardsBtn) ;
		choicePanel.add(exitBtn) ;
		this.add(choicePanel) ;
	}



}
