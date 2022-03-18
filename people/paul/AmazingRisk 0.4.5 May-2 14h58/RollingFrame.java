import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.Timer;
import javax.swing.BoxLayout;

/** THIS CLASS IS TOTALLY MADE BY THE GROUP SO WE ARE PROUD OF THAT*/

public class RollingFrame extends JFrame { 
	//paramètres de type primitif
	private int numberOfArmiesAtk, numberOfArmiesDef;
	private CountryButton cbAtk, cbDef;
	private ArrayList<Integer> dicesAtk=new ArrayList<>(), dicesDef=new ArrayList<>();
	private Sync sync;
	private boolean isIA=false;	//inclus dans sync, mais c'est plus visuel de le réécrire en attribut de la classe
	private String currentToe;	//inclus dans sync, mais c'est plus visuel de le réécrire en attribut de la classe
	private AmazingButton rollButton ;
	private boolean isRolling = false ;	//permet de ne pas redéclencher de roll pendant un autre roll

	//paramètres de type non primitif/graphique
	private JLabel atkLabel,defLabel ;
	private JPanel dicesAtkPanel;
	private JPanel dicesDefPanel;
	private JPanel resultsPanel ;

    public RollingFrame(CountryButton cbAtk, CountryButton cbDef, Sync sync)  {
		super(TitleOf.RollingFrame) ;
		//FRAME OPTIONS
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		final int width=400, height=500;
		setSize(new Dimension(width,height)) ;
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width-width, (Toolkit.getDefaultToolkit().getScreenSize().height/2)-height/2);
//		this.setLocationRelativeTo(null);
		this.setVisible(true);

		currentToe=sync.getCurrentEntity().getToe();
		if(currentToe.length()==3) {
			isIA=true;
		}

		this.sync=sync ;
		//instanciation du nombre d'armée qui va combattre
		this.numberOfArmiesAtk=cbAtk.getCountry().getArmies();
		this.numberOfArmiesDef=cbDef.getCountry().getArmies();
		this.cbAtk=cbAtk;
		this.cbDef=cbDef;

		//instanciation des arrayList de dés en leur donnait une capacité initiale ne pouvant dépasser 3
		for (int k=0 ; k<Math.min(numberOfArmiesAtk,3) ; k++) {
			dicesAtk.add(0);
		}
		for (int k=0 ; k<Math.min(numberOfArmiesDef,3) ; k++) {
			dicesDef.add(0);
		}

		//instanciation du AmazingButton pour lancer le roll
		if(!isIA) {
			rollButton=new AmazingButton("Roll !") ;
			rollButton.addActionListener(new RollListener(this)) ;
		}

		//instanciantion du JLabel affichant les armées combattant
		atkLabel = new JLabel(getDisplay(cbAtk));
		defLabel = new JLabel(getDisplay(cbDef));
		atkLabel.setHorizontalAlignment(JLabel.CENTER);
		atkLabel.setVerticalAlignment(JLabel.CENTER);
		defLabel.setHorizontalAlignment(JLabel.CENTER);
		defLabel.setVerticalAlignment(JLabel.CENTER);

		//instanciation du panel gérant le JLabel attacker/defender
		JPanel atkDefPanel = new JPanel(new GridLayout(1, 3));
		//panel central permettant d'afficher au centre le JLabel "attacker/defender" et le résultat des dés
		JPanel centralPanel = new JPanel();
		centralPanel.setLayout(new BoxLayout(centralPanel,BoxLayout.Y_AXIS));
		//panel d'affichage des dés et de leur résultat
		JPanel dicesPanel = new JPanel(new FlowLayout());
		//sous panels de dicesPanel (déAtk, déDef, resultat)
		dicesAtkPanel =new JPanel(new GridLayout(3,1)) ;
		dicesDefPanel =new JPanel(new GridLayout(3,1)) ;
		resultsPanel=new JPanel(new GridLayout(3,1)) ;

		//ajout des pays concernés par la bataille.
		JLabel arrowLabel = new JLabel(new ImageIcon("icons/smallArrow1.png")) ; //Petite flèche indiquant le sens de l'attaque
		arrowLabel.setHorizontalAlignment(JLabel.CENTER); //Je veux que le contenu du label soit toujours horizontalement centré.
		arrowLabel.setVerticalAlignment(JLabel.CENTER); //...et verticalement centré.
		atkDefPanel.add(atkLabel); //Pays attaquant
		atkDefPanel.add(arrowLabel) ; //Flèche
		atkDefPanel.add(defLabel);//Pays défenseur
		//ajout du panel du JLabel au panel central
		centralPanel.add(atkDefPanel) ;
		//ajout des panels sous jacents de dicesPanel à dicesPanel
		dicesPanel.add(dicesAtkPanel) ;
		dicesPanel.add(resultsPanel) ;
		dicesPanel.add(dicesDefPanel) ;
		//ajout de dicesPanel au panel central
		centralPanel.add(dicesPanel) ;
		//ajout du rollButton au nord de la fenetre
		if(!isIA) {
			this.add(rollButton,BorderLayout.SOUTH) ;
		}
		//ajout du panel central au centre de la fenetre
		this.add(centralPanel,BorderLayout.CENTER);
	}

	public String getDisplay(CountryButton cb){ //Cette méthode sert à récupérer la chaîne de caractères adéquate pour afficher les informations sur les pays belligérants.
		return "<html>"+cb.getCountry().getCountryName()+" troops: "+cb.getCountry().getArmies()+"<br>"+cb.getCountry().getContinent()+"<br>"+cb.getCountry().getOwner().getName()+"</html>" ;
	}

	public int getNumberOfArmiesAtk() {
		return numberOfArmiesAtk;
	}

	public int getNumberOfArmiesDef() {
		return numberOfArmiesDef;
	}

	public void roll() {
		if(!isRolling) {
			isRolling=true;
			firstStep();        //lance la méthode firstStep() qui lance les images de dés qui tournent
			Timer timer = new Timer();        //création d'un nouveau planificateur de tâches
			Task myTask = new Task(this);        //création d'une nouvelle tâche
			//myTask.addFrame(this) ;		//ajout de la fenêtre courante à notre tâche créée (qui permettra d'exécuter run())
			timer.schedule(myTask, 3000);        //le planificateur lance la méthode run() de Task au bout de 3000ms -> lance secondStep()
		}
	}
	
    public void firstStep(){
		//FIRST STEP: FALSE LOADING !
		emptyAll();
		for(int k=0 ; k<Math.min(numberOfArmiesAtk-1,3) ; k++) { //On ne veut pas se retrouver dans la situation où l'attaquant a trois armées, et le défenseur plus que trois et que le défenseur le zigouille(0 armées).
			dicesAtkPanel.add(getLoadDice()) ;	//on ajoute pour chaque dé du joueur attaquant un LoadDice
		}

		for(int k=0 ; k<Math.max(Math.min(numberOfArmiesAtk-1,3),Math.min(numberOfArmiesDef,3)) ; k++) {
			resultsPanel.add(getLoadImage()) ;		//on ajoute le nombre max de LoadImage au panel des résultats (nombre max correspondant au plus grand nombre de dés disponibles)
		}

		for(int k=0 ; k<Math.min(numberOfArmiesDef,3); k++) {
			dicesDefPanel.add(getLoadDice()) ;	//on ajoute pour chaque dé du joueur défendant un LoadDice
		}

		validate(); repaint();
	}
    
	public void secondStep() {
		//SECOND STEP : REALLY ROLLING THE DICES !
		emptyAll();

		//lancé des dés attaquants
		for (int k = 0; k < Math.min(numberOfArmiesAtk-1,3); k++) { //On ne veut pas se retrouver dans la situation où l'attaquant a trois armées, et le défenseur plus que trois et que le défenseur le zigouille(0 armées).
			dicesAtk.add((int)(1 + Math.random() * 6));
		}

		//lancé des dés défenseurs
		for (int k = 0; k < Math.min(numberOfArmiesDef,3); k++) {
			dicesDef.add((int)(1 + Math.random() * 6));
		}

		//tri des jeux de dés
		dicesAtk.sort(Collections.reverseOrder());
		dicesDef.sort(Collections.reverseOrder());

		//ajout des panels de dé correspondant à chaque jeté de dé
		for (Integer integer : dicesAtk) {
			dicesAtkPanel.add(getDice(integer));
		}
		for (Integer integer : dicesDef) {
			dicesDefPanel.add(getDice(integer));
		}

		//actions à faire en fonction de qui a gagné (par dé)
		for (int k = 0; k < Math.min(dicesAtk.size(), dicesDef.size()); k++) {
			if (dicesAtk.get(k) > dicesDef.get(k)) {
				numberOfArmiesDef--;
				resultsPanel.add(getResultArrow(1));
			} else {
				numberOfArmiesAtk--;
				resultsPanel.add(getResultArrow(2));
			}
			refreshButtons() ;
		}

		atkLabel.setText(getDisplay(cbAtk));
		defLabel.setText(getDisplay(cbDef));
		//atkDefLabel.setText("AttackerArmy : "+ numberOfArmiesAtk + " | DefenderArmy : "+ numberOfArmiesDef);

		//Attaque réussie ! On passe à l'étape de choix du nombre d'armées à laisser dans chaque pays mis en jeu
		if(numberOfArmiesDef==0 && numberOfArmiesAtk>1){
			thirdStep();
		} else if(numberOfArmiesAtk==1){
			//Si l'IA perd son attaque, que fait-elle ?
			switch (currentToe) {
				//AI1 : elle passe au joueur d'après
				case "AI1" :
					sync.enterTheGameLoopAgain();
					break;
				//AI2 : ???
				case "AI2" :
					//tâches à faire si l'IA2 perd son attaque
					break;

				//AI3 : ???
				case "AI3" :
					//tâches à faire si l'IA3 perd son attaque
					break;
				default : break;
			}
			this.dispose() ;
		} else {	//si l'attaquant n'a encore ni gagné, ni perdu le combat, on attend un nouveau clic...
			//...Partie IA : mais que font les IA à la fin d'un lancer de dé ?
			switch (currentToe) {
				//AI1 : elle arrête l'attaque et relance une attaque aléatoire
				case "AI1":
					this.dispose();
					sync.gamePhase4AI1Task();
					break;
				//AI2 : ???
				case "AI2":
					//tâches à faire à la fin d'un lancer de dé pour l'IA 2
					break;

				//AI3 : ???
				case "AI3":
					//tâches à faire à la fin d'un lancer de dé pour l'IA 3
					break;
			}
		}
		validate();
		repaint();

		isRolling=false;	//autorise le reroll
	}


	public void thirdStep(){ //Third step when you win the battle : you have to move your armies in the won country.
    	//cette partie est valable quel que soit le fait qu'on soit joueur ou IA : si on gagne le combat, alors on récupère le pays
    	cbDef.getCountry().setOwner(cbAtk.getCountry().getOwner()); //Le pays défenseur change de propriétaire.
		cbDef.repaint(); //L'affichage du pays est actualisé.

		//si le c'est un joueur qui a lancé l'attaque, alors on lui met un interface pour choisir les armées qu'il veut laisser dans les pays
		if(!isIA) {
			JSlider slider = new JSlider(1, numberOfArmiesAtk - 1); //Création d'un slider (curseur <----O--> pour régler le nombre d'armées déplacées
			slider.setMinorTickSpacing(1); //Entre chaque unité (d'armées) du slider, on veut une graduation non chiffrée
			slider.setMajorTickSpacing((int) (1 + 0.1 * (numberOfArmiesAtk))); //Entre chaque intervalle bien défini, on veut une graduation chiffrée.
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);

			//INITIALISATION OF THE VALUE BEFORE MODIFYING THANKS TO THE ChangeListener
			cbDef.getCountry().setArmies(slider.getValue()); //On veut que le pays conquis ait au moins quelques armées/
			cbAtk.getCountry().setArmies(numberOfArmiesAtk - slider.getValue()); //On veut retrancher le nombre d'armées déplacées au nombre d'armées de pays attaquant
			cbDef.repaint();
			cbAtk.repaint(); //Actualisons l'affichage de ces pays.
			atkLabel.setText(getDisplay(cbAtk)); //Actualisons égalements les deux labels d'informations sur les pays belligérants.
			defLabel.setText(getDisplay(cbDef));

			//On ajoute un listener au slider afin que quand on déplace le curseur, les changements soient faits en LIVE.
			slider.addChangeListener(e -> {//Si l'état du slider a changé (le curseur est déplacé)...
				cbDef.getCountry().setArmies(((JSlider) e.getSource()).getValue()); //Alors on change le nombre d'armées du pays conquis..
				cbAtk.getCountry().setArmies(numberOfArmiesAtk - ((JSlider) e.getSource()).getValue());//...et du pays qui a conquis.
				cbDef.repaint();
				cbAtk.repaint();//On actualise l'affichage des boutons associés aux pays belligérants.
				atkLabel.setText(getDisplay(cbAtk)); //Ainsi que les deux labels d'infos associés.
				defLabel.setText(getDisplay(cbDef));
			});

			this.remove(rollButton); //On enlève le bouton permettant de lancer les dès.
			JPanel movePanel = new JPanel(new GridLayout(2, 1)); //On crée un nouveau JPanel qui permettra d'afficher le Label de consigne de jeu et le slider associé.
			JPanel includedPanel = new JPanel(new FlowLayout()); //Pour centrer le Label de consigne, on ne le met pas directement dans le movePanel (pas assez beau), mais on veut un FlowLayout().
			JPanel includedPanel2 = new JPanel(new FlowLayout()); //Même idée, on ne place pas le slider directement dans le movePanel mais on veut le mettre dans un FlowLayout() pour un affichage "joli".
			JLabel moveLabel = new JLabel("<html>Select the amount of troop to move:</html>"); //Voici le JLabel de consigne de jeu
			includedPanel.add(moveLabel);//On l'ajoute dans un panel à FlowLayout()...
			movePanel.add(includedPanel);//Qu'on ajoute lui-même dans le movePanel.
			includedPanel2.add(slider);//On ajoute le slider dans un panel à FlowLayout()...
			movePanel.add(includedPanel2);//Qu'on ajoute lui-même dans le movePanel. C'est bon.
			this.add(movePanel, BorderLayout.NORTH);
			AmazingButton closeButton = new AmazingButton("Finish moving the troops !"); //Nouveau bouton pour terminer le déplacement en fermant la fenêtre (équivaut à la fermer avec la croix).
			closeButton.addActionListener(new CloseListener(this));
			this.add(closeButton, BorderLayout.SOUTH);
		}

		//si c'est une IA qui vient de remporter le combat... que fait-elle ?
		else {
			switch (currentToe) {
				//si c'est l'IA1 : elle met toutes ses armées dans le pays conquis et ferme la fenetre
				case "AI1" :
					cbDef.getCountry().setArmies(numberOfArmiesAtk-1);
					cbAtk.getCountry().setArmies(1);
					cbAtk.repaint();
					cbDef.repaint();
					this.dispose();
					sync.enterTheGameLoopAgain();
					break ;

				//si c'est l'IA2 : ???
				case "AI2" :
					/*A CODER*/
					break ;

				//plus on est de fou plus on rit : go faire une IA3
				case "AI3" :
					/*A CODER*/
					break;
			}
		}
	}

	public void refreshButtons(){
		cbDef.getCountry().setArmies(numberOfArmiesDef) ;
		cbAtk.getCountry().setArmies(numberOfArmiesAtk) ;
		cbDef.validate() ; cbDef.repaint() ;
		cbAtk.validate() ; cbAtk.repaint() ;
	}
		
	public DisplayPanel getLoadDice() {
		DisplayPanel toReturn = new DisplayPanel(new ImageIcon("icons/loadDice.gif"),70,70) ;
		toReturn.setPreferredSize(new Dimension(70,70)) ;
		return toReturn ;
	}

	public DisplayPanel getLoadImage() {
		DisplayPanel toReturn = new DisplayPanel(new ImageIcon("icons/loading.gif"),70,70) ;
		toReturn.setPreferredSize(new Dimension(70,70)) ;
		return toReturn ;
	}

	public DisplayPanel getResultArrow(int c) {
		DisplayPanel toReturn = new DisplayPanel(new ImageIcon("icons/arrow"+c+".png"),70,70) ;
		toReturn.setPreferredSize(new Dimension(70,70)) ;
		return toReturn ;
	}

	public DisplayPanel getDice(int k) {

		DisplayPanel toReturn = new DisplayPanel(new ImageIcon("icons/dice"+k+".png"),70,70) ;
		toReturn.setPreferredSize(new Dimension(70,70)) ;
		return toReturn ;
	}

	/*cette méthode permet de vider les pannel à afficher (opération nécessaire au début de firstStep ET secondStep)
	* */
	public void emptyAll() {
		dicesAtk.clear();
		dicesDef.clear();
		dicesAtkPanel.removeAll() ;
		dicesDefPanel.removeAll() ;
		resultsPanel.removeAll() ;
	}
	
	public void dispose(){
		cbAtk.setCircleAround(false) ;
		cbDef.setCircleAround(false) ;
		sync.attackEnd();
		super.dispose() ;
	}
}
