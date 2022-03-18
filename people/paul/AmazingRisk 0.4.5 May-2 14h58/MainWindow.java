import java.awt.*;
import javax.swing.*;
import java.util.LinkedList;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.io.IOException;

public class MainWindow extends JFrame {

	private Sync sync;
	private double proportionnalPictureCoeff ;
	private int w, h;
	private ImageIcon chosenMap ;
	private DisplayPanel mapPanel ;
	private LinkedList<Country[]> c1c2;	//liste des pays liés à repeindre

	/**We'll be used in south panel for the current player*/

	private LinkedList<CountryButton> countryButtons;
	private JPanel southPanel, gamePanel ;
	private AmazingButton stepButton, optionsButton ;
	private StepLabel stepLabel ;
	private JToolBar toolbar ;

	public MainWindow(ImageIcon chosenMap, LinkedList<Country> countries,  Sync sync) {
		//Instanciation des parametres de la fenetre
		super(TitleOf.MainWindow) ;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200,800);
		setLocationRelativeTo(null);
		setVisible(true);

		this.sync=sync;

		//Instanciation du panneau principal accueillant tous les autres.
		gamePanel=new JPanel(new BorderLayout()) ;

		//Instanciation du numero de map ainsi que ajout de la liste des pays de la map choisie
		this.chosenMap= chosenMap ; //Choice of the map

		//Instanciation du coefficient de proportionnalité de l'image (pour conserver les proportions lors du redimensionnement)
		proportionnalPictureCoeff=(double)(chosenMap.getIconWidth())/chosenMap.getIconHeight() ;

		/**MAP PANEL*/
		//Création du panel avec la map
		mapPanel= new DisplayPanel(chosenMap,getWidth(), getHeight());
		//Création de la bordure
		Border mapBorders = BorderFactory.createLineBorder(Color.black, 1);
		//Ajout de la bordure au panel avec la map
		mapPanel.setBorder(mapBorders) ;
		//Ajout d'un listener pour que quand on fait un click-droit, on ait le classement des joueurs.
		mapPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e) && sync.dataAvaibility){
                    (new PopupMenu(sync,0)).show( e.getComponent(), e.getX(), e.getY() );
                }
			}
		});

		//Création des boutons pour chacun de pays de la liste
		countryButtons =new LinkedList<>() ;
		for(Country country : countries){
			country.addSync(sync) ;
			CountryButton cb = new CountryButton(country) ;
			countryButtons.add(cb) ;
			mapPanel.add(cb);
		}

		/**PANEL NORTH*/
		//Création d'un northPanel potentiellement utilisable
		JPanel northPanel = new JPanel() ;

		/**PANEL SOUTH*/
		//Création du label d'affichage des infos
		stepLabel=new StepLabel("Step Infos",sync) ;
		//Création d'un bouton qui servira à changer de tour
		stepButton=new AmazingButton("Next Step") ;
		stepButton.addActionListener(new FinishMyTurnListener(sync));
		//Création d'un bouton fourre-tout qui permettra d'avoir plusieurs options relatives à la partie en cours.
		optionsButton=new AmazingButton("Options") ;
		optionsButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				PopupMenu OptionsPopup = new PopupMenu(sync) ;
				Dimension d = OptionsPopup.getPreferredSize() ;
				OptionsPopup.show(e.getComponent(),0,-d.height);
			}
		});
		
		//Création du southPanel en BorderLayout
		southPanel=new JPanel(new BorderLayout()) ;
		//Ajout du label d'info et du bouton NextStep au southPanel et aussi du bouton Options au southPanel
		southPanel.add(stepButton, BorderLayout.EAST) ;
		southPanel.add(stepLabel, BorderLayout.CENTER) ;
		southPanel.add(optionsButton, BorderLayout.WEST) ;

		//Ajout des panels au BorderLayout principal
		gamePanel.add(northPanel, BorderLayout.NORTH) ;
		gamePanel.add(mapPanel, BorderLayout.CENTER);	//GROS PROBLEME CONCERNANT LE MAPPANEL : LA CARTE EST BIEN PAINTED MAIS JUSTEMENT ELLE N APPARTIENT A AUCUN LAYOUT
		gamePanel.add(southPanel, BorderLayout.SOUTH) ;
		
		//Ajout de l'écouteur de redimensionnement
		this.addComponentListener(new IfResized(this)) ;
        
        //Ajout de panel de jeu à la fenêtre (on pourrait ajouter d'autres choses comme des barres d'outils si le besoin d'en fait sentir (mais on mise sur une interface épurée).
        this.add(gamePanel, BorderLayout.CENTER) ;
		
		//Initialisation de la LinkedList des couples de pays à relier.
		c1c2=new LinkedList<>() ;
		resizePanel() ;	
	}

	/**GETTERS*/
	public LinkedList<CountryButton> getCountryButtons() {
		return countryButtons;
	}
	public DisplayPanel getMapPanel(){
		return mapPanel;
	}
	public void resizePanel() {
		w = mapPanel.getWidth() ;
		h = mapPanel.getHeight() ;
		if(w>=proportionnalPictureCoeff*h){
			w=(int)(proportionnalPictureCoeff*h) ;
		} else {
			h=(int)(w/proportionnalPictureCoeff) ;
		}
		mapPanel.setSize(w,h);
		for(CountryButton countrybutton : countryButtons) {
			int x = (int)(countrybutton.getCountry().getCoeffX()*w) ;
			int y = (int)(countrybutton.getCountry().getCoeffY()*h) ;
			countrybutton.setBounds(x,y,30,30) ;
			mapPanel.add(countrybutton) ;
		}
		mapPanel.validate() ;
		mapPanel.repaint() ;
		this.validate() ;
		this.repaint() ;
		drawLines() ;
	}
	public void updateStepLabel(String txt){
		stepLabel.setText(txt) ;
		stepLabel.setToolTipText(txt) ;
		stepLabel.repaint() ;
	}
	public void clearLinkedCountries() {
		c1c2.clear();
		mapPanel.removeLine() ;
	}
	public void addLineBetween(Country attackingCountry, Country defenderCountry){
		c1c2.add(new Country[]{attackingCountry,defenderCountry}) ;
	}
	//Utilise les attributs w et h actualisés dans la fenetre pour draw à la bonne taille
	public void drawLines(){
		try {
			int radius=sync.findAssociatedButton((c1c2.get(0))[0]).getRadius() ;
			for(Country[] couple : c1c2){
				int x1= (int)((w*couple[0].getCoeffX())+radius);
				int y1= (int)((h*couple[0].getCoeffY())+radius);
				int x2= (int)((w*couple[1].getCoeffX())+radius);
				int y2= (int)((h*couple[1].getCoeffY())+radius);
				mapPanel.setLine(x1,y1,x2,y2,sync.findAssociatedButton(couple[0]).getColour()) ;	//on récupère la couleur du pays attaquant
			}
		} catch (Exception ignored) {}
	}
}
