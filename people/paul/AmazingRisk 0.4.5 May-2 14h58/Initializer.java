import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.util.LinkedList;

//import java.awt.event.ComponentEvent;
//import java.awt.event.ComponentListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JComboBox; //import for the dropdown lists

public class Initializer extends JFrame {

	private final int NB_PLAYERS_MAX=8 ;
	private boolean gameIsReadyToStart=false ;

	//FOR THE CHOICE OF MAP
	private JPanel panelOfMapChoices, southPanelForStart ;
	private LinkedList<ImageIcon> maps = Maps.getMaps() ;
	private JLabel mapToChoose ;
	private JPanel gridnorthwestpanel, northPanel ;
	private JLabel selectedMap ;
	private int currentMap=0;		//0 <=> no map

	//FOR THE CHOICE OF THE ENTITIES
	private JLabel chooseNbOfEntitiesLabel ;
	private JPanel chooseNbOfEntitiesPanel, flowCenterPanel, gridCenterPanel ;

	
	private JComboBox<String>[] theColours;
	private JComboBox<String>[] theToe ;
	private JTextField[] theNames ;
	private JCheckBox[] theCheckBoxes ;
	private JPanel[] theCenterPanels ;
	
	//TO VALIDATE :
	private AmazingButton start ;
	private JPanel[] warningPanels ;
	private LinkedList<Entity> entitiesforTheGameList;
	
	public Initializer(){
		super(TitleOf.Initializer) ;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500,800);
		setLocationRelativeTo(null);
		setVisible(true);

		//CHOICE OF MAP//
		gridnorthwestpanel= new JPanel(new GridLayout(2,1)) ;
		northPanel= new JPanel(new FlowLayout()) ;
		panelOfMapChoices=new JPanel() ;
		panelOfMapChoices.setLayout(new FlowLayout()) ;
		mapToChoose= new JLabel("Please choose a map : ") ;
		selectedMap=new JLabel("< No selected map >") ;

		for (ImageIcon map : maps) {

			double coeff = (double) (map.getIconWidth()) / map.getIconHeight();
			int height = 100;
			int width = (int) (coeff * height);
			String btnDescription = map.getDescription();
			int debut = btnDescription.indexOf("/m");
			int fin = btnDescription.indexOf(".jpg", debut);
			btnDescription = btnDescription.substring(debut + 1, fin);
			IconButton btn = new IconButton(btnDescription, map, width, height);
			btn.addActionListener(new MapChoiceListener(this, btn));
			btn.setToolTipText(btnDescription) ;
			btn.setPreferredSize(new Dimension(width, height));
			panelOfMapChoices.add(btn);
		}
		//END OF CHOICE OF MAP//
		
		//CHOICE OF ENTITIES//
		chooseNbOfEntitiesLabel= new JLabel("<html>Please check the boxes to make the associated player/AI enter the game. <br> There must be between 2 and " + NB_PLAYERS_MAX + " players/AIs. </html>") ; //<br> equals to "\n' or system.out.println()...
		chooseNbOfEntitiesPanel=new JPanel() ;

		flowCenterPanel=new JPanel() ;
		gridCenterPanel=new JPanel(new GridLayout((2*NB_PLAYERS_MAX-1),1,7,7)) ;

		//Utilisation d'un FlowLayout(chooseNbOfEntites) intermédiaire pour centrer le texte
		chooseNbOfEntitiesPanel.add(chooseNbOfEntitiesLabel) ;
		gridCenterPanel.add(chooseNbOfEntitiesPanel) ;
		flowCenterPanel.add(gridCenterPanel) ;
		add(flowCenterPanel, BorderLayout.CENTER) ;
		

		//Organization of the start-window
		gridnorthwestpanel.add(mapToChoose) ;
		gridnorthwestpanel.add(selectedMap) ;
		northPanel.add(gridnorthwestpanel) ;
		northPanel.add(panelOfMapChoices) ;
		add(northPanel, BorderLayout.NORTH) ;
		add(new JScrollPane(northPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.NORTH) ;
		
		//CENTER ORGANIZATION
		theCenterPanels= new JPanel[NB_PLAYERS_MAX] ; theColours = new JComboBox[NB_PLAYERS_MAX] ; theNames=new JTextField[NB_PLAYERS_MAX] ; theCheckBoxes=new JCheckBox[NB_PLAYERS_MAX] ; theToe=new JComboBox[NB_PLAYERS_MAX] ;

		for(int w=0;w<NB_PLAYERS_MAX;w++) {
			theCenterPanels[w]= new JPanel() ; theCheckBoxes[w]=new JCheckBox(""+(w+1)+ " ", false) ;
			theColours[w]=new JComboBox<>(new String[]{"Select...","Blue","Green","Red","Yellow","Magenta", "Cyan","Pink","White","Gray","Orange"}) ;
			theNames[w]=new JTextField("Player/AI n°"+(w+1)) ;
			theNames[w].setPreferredSize(new Dimension(150,33)) ;
			theToe[w]= new JComboBox<>(new String[]{"Player","AI"}) ;
			theCenterPanels[w].add(theCheckBoxes[w]) ; theCenterPanels[w].add(theToe[w]) ; theCenterPanels[w].add(new JLabel("Name : ")) ;
			theCenterPanels[w].add(theNames[w]) ; theCenterPanels[w].add(new JLabel("Colour : ")) ;  theCenterPanels[w].add(theColours[w]) ;
			gridCenterPanel.add(theCenterPanels[w]) ; theColours[w].addActionListener(new ColourChoiceListener(this,theColours[w])) ;
		}

		add(new JScrollPane(flowCenterPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER) ;
		

		/*Création du bouton start avec son écouteur et ajout au panel tout en bas*/
		start=new AmazingButton("Start an amazing game");
		start.setToolTipText("Click if you are ready to start") ;
		start.addActionListener(new LocalStartListener(this)) ;
		southPanelForStart=new JPanel(new FlowLayout()) ;
		southPanelForStart.add(start) ;
		add(southPanelForStart, BorderLayout.SOUTH) ;

		/*Création des 6 cases de tableau pour les messages d'erreurs*/
		warningPanels = new JPanel[6] ;

		for(int l=0; l<warningPanels.length ; l++) {
			warningPanels[l]= new JPanel() ;
			warningPanels[l].setBackground(Color.RED) ;
		}
		//warningPanel.setBackground(Color.RED) ; warningPanel2.setBackground(Color.RED) ; warningPanel3.setBackground(Color.RED) ;
		warningPanels[0].add(new JLabel("You should chose between 2 and " + NB_PLAYERS_MAX +" players/AIs.")) ;
		warningPanels[1].add(new JLabel("You might not choose the same colour.")) ;
		warningPanels[2].add(new JLabel("You should select a colour for each player/AI.")) ;
		warningPanels[3].add(new JLabel("You might not choose the same name.")) ;
		warningPanels[4].add(new JLabel("Your name may have between 3 and 17 caracters.")) ;
		warningPanels[5].add(new JLabel("You should select a map"));
		
		entitiesforTheGameList = new LinkedList<>()  ;
		
		validate() ;
		repaint() ;
		
	}
	
	public void setChosenMap(String mapName){ //In order to display the selected map
		selectedMap.setText("< "+mapName+" >") ;
		int debut = mapName.indexOf("_") + 1;
		currentMap=Integer.parseInt(mapName.substring(debut));
		validate(); repaint();
	}
	
	public void setComboBoxColour(JComboBox mycb)  {
		String colour = mycb.getSelectedItem().toString();
		mycb.setBackground(ColorFactory.getColorFromString(colour));
	}
	
	public void start() throws IOException {

		boolean isEnoughEntity=true ;
		boolean isDifferentColour=true;
		boolean isColourSelected=true;
		boolean isDifferentName=true;
		boolean isNoCaracterProblem=true;

		//STEP 1 : VERIFY THE NUMBER OF PLAYERS/AI
		int nb_players=0 ;
		for (JCheckBox theCheckBox : theCheckBoxes) {
			if (theCheckBox.isSelected()) { //It is a boolean (if the check box is checked
				nb_players++; //It counts the number of selected players/AIs by using the check boxes
			}
		}

		gridCenterPanel.remove(warningPanels[0]) ;
		if(nb_players<2){
			gridCenterPanel.add(warningPanels[0]);
			isEnoughEntity = false;
		}
		validate() ; repaint() ;

		//STEP 2 : VERIFY IF ALL THE PLAYERS/AIs HAVE A SELECTED COLOUR AND THEN, IF EVERYONE HAS A COLOR, VERIFY IF SOME PLAYERS/AIs HAVEN'T THE SAME COLOUR
		for(int w=0 ; w<theColours.length ; w++){
			try {
				if(theCheckBoxes[w].isSelected() && theColours[w].getSelectedItem().toString().equalsIgnoreCase("Select...")){
					isColourSelected=false ;
				}
			} catch (Exception e) { e.printStackTrace();}
		}

		gridCenterPanel.remove(warningPanels[2]) ;
		if(!isColourSelected) {
			gridCenterPanel.add(warningPanels[2]) ;
		} else {
			for(int w=0 ; w<theColours.length ; w++){
				for(int k=0 ; k<theColours.length ; k++){
					if(k!=w && theCheckBoxes[w].isSelected() && theCheckBoxes[k].isSelected()
							&& theColours[w].getSelectedItem().toString().equalsIgnoreCase(theColours[k].getSelectedItem().toString())
							&& !theColours[w].getSelectedItem().toString().equalsIgnoreCase("Select...")
							&& !theColours[k].getSelectedItem().toString().equalsIgnoreCase("Select...")) {
						isDifferentColour=false;
					}
				}
			}
			validate() ; repaint() ;

			gridCenterPanel.remove(warningPanels[1]) ;
			if(!isDifferentColour){
				gridCenterPanel.add(warningPanels[1]) ;
			}
			validate() ; repaint() ;
		}

		//STEP 3 : VERIFY IF ALL THE PLAYERS/AIs HAVEN'T THE SAME NAMES
		for(int w=0 ; w<theNames.length ; w++){
			for(int k=0 ; k<theNames.length ; k++){
				if(k!=w && theCheckBoxes[w].isSelected() && theCheckBoxes[k].isSelected() 
				&& theNames[w].getText().equalsIgnoreCase(theNames[k].getText())
				&& theNames[w].getText().length()>=3 && theNames[w].getText().length()<=17		//QUEL INTERET ICI DE COMPARER LA TAILLE DU TXT ? CE N EST PAS SENSE ETRE LE STEP 5 ?
				&& theNames[k].getText().length()>=3 && theNames[k].getText().length()<=17) {
					isDifferentName=false ;
				}
			}
		}
		gridCenterPanel.remove(warningPanels[3]) ;
		if(!isDifferentName){
			gridCenterPanel.add(warningPanels[3]) ;
		}
		validate() ;
		repaint() ;

		//STEP  4 : VERIFY IF ALL THE PLAYERS/AIs HAVE A RIGHT NAME

		for(int w=0 ; w<theNames.length ; w++){

			if(theCheckBoxes[w].isSelected()
			&& theNames[w].getText().length()<3 || theNames[w].getText().length()>17) {
				isNoCaracterProblem=false ;
			}
		}
		gridCenterPanel.remove(warningPanels[4]) ;
		if(!isNoCaracterProblem){
			gridCenterPanel.add(warningPanels[4]) ;
		}
		validate() ;
		repaint() ;

		//STEP 5 : VERIFY IF A MAP IS SELECTED
		if(currentMap==0) {
			gridCenterPanel.remove(warningPanels[5]);
			gridCenterPanel.add(warningPanels[5]);
			validate() ; repaint() ;
		} else {
			gridCenterPanel.remove(warningPanels[5]);
			validate();repaint();
		}

		if(isEnoughEntity && isDifferentColour && isColourSelected && isDifferentName && isNoCaracterProblem && currentMap!=0) {
			gameIsReadyToStart=true;
		}

		if(gameIsReadyToStart) {
				
			for(int w=0; w<theCheckBoxes.length; w++) {
				Entity tmpEntity = null;
				if (theCheckBoxes[w].isSelected()) {
					if (theToe[w].getSelectedItem().equals("Player")) {
						tmpEntity = new Player(theNames[w].getText(), (String) theColours[w].getSelectedItem());
					} else if (theToe[w].getSelectedItem().equals("AI")) {
						//PENSER A RAJOUTER UNE JCOMBO BOX DANS L'AFFICHAGE PERMETTANT DE CHOISIR LA DIFFICULTE DE L'IA
						//donc rajouter aussi une condition ici qui regarde quel est le niveau de difficulté choisi (avec un switch par exemple)

						tmpEntity = new AI1(theNames[w].getText(), (String) theColours[w].getSelectedItem());
					}
				}
				if(tmpEntity!=null) {
					entitiesforTheGameList.add(tmpEntity) ;       //ajoute les différentes Entity à la liste des entity que Sync possède en static
				}
			}
			new Sync(currentMap, entitiesforTheGameList);	//pose le premier joueur/AI courant de manière aléatoire

			this.dispose();
		}
	}

	public void dispose() {
		if(!gameIsReadyToStart) {
			AmazingMenu am = new AmazingMenu();
			am.setVisible(true);
			maps.clear();	//permet de ne pas faire de map en doublon si l'on a fermé puis rouvert Initializer
		}
		super.dispose();
	}

}
