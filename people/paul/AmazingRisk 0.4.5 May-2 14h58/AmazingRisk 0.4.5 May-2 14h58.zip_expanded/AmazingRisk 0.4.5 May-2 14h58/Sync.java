import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.swing.*;
import java.util.LinkedList;
import java.io.IOException;
import java.util.Timer;

public class Sync {


	/**ATTRIBUTS*/

	/*éléments accessibles depuis toutes les classes ayant simplement fait un new Sync*/
    private int gamePhase;
    private Entity currentEntity;
    private World world ;
    private LinkedList<Continent> continents ;
    private LinkedList<Country> countries ;

    /*éléments définissant Sync, auxquels il peut donc accéder facilement pour transmettre des informations*/
    private Country attackingCountry, defenderCountry ;
    private MainWindow mainWindow;
    private boolean chooseStep =false ;
    
    protected LinkedList<Entity> entitiesList ;
    protected RankingDatas rkDatas ;
    protected boolean dataAvaibility = false ;


    /**CONSTRUCTEURS*/

    public Sync(int mapNumber, LinkedList<Entity> entitiesForTheGameList) throws IOException {
        world = Maps.getWorld(mapNumber);
        continents = world.getWorldContinents();
        countries = world.getAllCountries();

		ImageIcon chosenMap = Maps.getMap(mapNumber);

    	this.mainWindow=new MainWindow(chosenMap, countries, this) ;
        this.entitiesList=entitiesForTheGameList ;
        addSyncToAllAI();		//en effet, lorsque les entity sont créées (dans Initializer), Sync n'existe pas encore et on ne peut donc pas directement leur ajouter Sync -> cette méthode private de Sync permet donc d'ajouter Sync à toutes les AI
        
        rkDatas = new RankingDatas(entitiesList, countries) ;
        begin() ;
	}


	/**METHODES UTILES UNIQUEMENT AU CONSTRUCTEUR*/

	//permet d'ajouter Sync à toutes les IA
	private void addSyncToAllAI() {
   		for(Entity entity : entitiesList) {
   			if (entity instanceof AI1) {
				((AI1)entity).addSync(this);
			}
		}
	}

	/*methode de lancement de Sync, qui initialise la phase de jeu à 0, choisi un joueur aléatoirement pour débuter le jeu puis lance la phase 0 */
	public void begin() {
		
		gamePhase=0 ;
		int a = (int)(Math.random()*entitiesList.size());
		
		currentEntity=entitiesList.get(a) ;
		entitiesList.remove(a) ;
		entitiesList.addFirst(currentEntity) ; //Le joueur/IA choisi au hasard devient le premier joueur de la liste des joueurs/IA.
		
		gamePhase0Tasks();
	}


	/**GETTERS*/
	public Entity getCurrentEntity() {
		return currentEntity;
	}

	public MainWindow getMainWindow(){
		return mainWindow ;
	}

	public LinkedList<Country> getCountries() {
		return countries;
	}

	public CountryButton findAssociatedButton(Country country){
		CountryButton toReturn=null ;
		for(CountryButton cb : mainWindow.getCountryButtons()){
			if(country.equals(cb.getCountry())){
				toReturn=cb;
				break;
			}
		}
		return toReturn ;
	}


	/**ECOUTE DU JOUEUR (ROOTAGE VERS LES METHODES DE JEU)*/

	public void countryClicked(Country c) {
		Entity owner = c.getOwner() ;	//permet simplement d'avoir localement le possesseur du pays cliqué pour plus de facilité/rapidité

		//ajout d'une armée lors de la phase 0 pour le joueur en cours, ce qui lui attribue le pays
		if(currentEntity.getToe().length()!=3){ //Les chaînes de taille 3 sont les AIx avec x un integer. On ne veut pas que l'on puisse cliquer pour un AI.
			if(owner==null && gamePhase==0) {
				c.addArmies();
				c.setOwner(currentEntity) ;
				gamePhase0Tasks();	//ces methodes peuvent paraître bizarre alors qu'on a déjà effectué des actions dans cette boucle
				// mais l'intérêt est de séparer les tâches qui impliquent le pays des tâches à faire dans Sync (on évite de passer le Country à une autre méthode supplémentaire)
			}

			//
			else if(gamePhase==1 && owner.equals(currentEntity)) {	//on met la condition de gamePhase en premier pour éviter un nullPointerException sur la 2e condition
				c.addArmies();
				currentEntity.removeSomeArmies(1);
				gamePhase1Tasks();
			}

			else if (owner.equals(currentEntity) && gamePhase==3) {
				c.addArmies();
				currentEntity.removeSomeArmies(1);
				gameLoop();
			}

			else if(chooseStep  && gamePhase==4) {
				countryTargetedInPhase4(c);
			}
		} else {
			System.out.println("You have not the right to do that") ; //à modifier.
		}
			
	}


	/**METHODES DE JEU*/

	/**PHASE 0*/

	//tâches de la phase 0 : tant que tous les pays ne sont pas attribués, on la relance
	public void gamePhase0Tasks() {
		boolean nextStep = true;
		for (Country country : countries) {
			if (country.getOwner() == null) {
				nextStep = false;
				break;            //le break permet de sortir de la boucle une fois la condition vérifiée : puisque la boucle est un ForEach, on ne peut mettre un while à la place du break
			}
		}
		if (nextStep) {
			gamePhase = 1;
			initializeStock();	//initialisation des stocks de départ pour la phase 1 -> ne s'exécute bien qu'une seule fois
			//refreshOwnedCountries();
			gamePhase1Tasks();
		} else {
			nextEntity();
			mainWindow.updateStepLabel(currentEntity.toString() + ", please choose a country for you.");

			if ((currentEntity.getToe()).equals("AI1")) {
				Timer timer = new Timer() ;
				Task AI1TaskPhase0 = new Task(this,1,0) ; //AI number 1 and gamePhase nb 0.
				timer.schedule(AI1TaskPhase0, 500);
			}
		}
	}

	//tâches de l'IA lors de la phase 0 (s'attribuer un pays : TO DO -> ajouter un Timer pour pas que l'IA fasse la tache instantanément (genre 1 sec))
	protected void gamePhase0AI1Tasks() {
		Country c = ((AI1) currentEntity).getCountryInPhase0();
		c.addArmies();
		c.setOwner(currentEntity);
		findAssociatedButton(c).repaint();
		gamePhase0Tasks();
	}

	//The Armies to give for each player at the beginning depends on the number of players/AIs and on the nb of countries.
	private void initializeStock() {
		int beginStockArmies =  50-entitiesList.size()*5;
		for (Entity entity : entitiesList) {
			entity.setArmiesInStock(beginStockArmies);
		}
	}


	/**PHASE 1*/

	//tâches de la phase 1 : tant qu'un des joueur à des armées en stock à déposer, on la relance
	public void gamePhase1Tasks() {
		boolean nextStep=true ;
		for(Entity entity : entitiesList){
			if(entity.getArmiesInStock()>0){
				nextStep=false ;
				break;
			}
		}
		if(nextStep){
			gamePhase=2 ;
			nextEntity() ;
			gameLoop() ;
		}else{
			refreshOwnedCountries();
			nextEntity();
			mainWindow.updateStepLabel(currentEntity.toString()+", please add an army in one your countries ("+currentEntity.getArmiesInStock()+" armies to place).") ;
			if(currentEntity.getToe().equals("AI1")) {
				Timer timer = new Timer() ;
				Task AI1TaskPhase1 = new Task(this,1,1) ; //AI number 1 and gamePhase nb 0.
				timer.schedule(AI1TaskPhase1, 100);
			}
		}
	}

	//Ce que fait Sync avec le pays demandé
	public void gamePhase1AI1Tasks() {
		Country c = ((AI1)currentEntity).getCountryInPhase1();
		c.addArmies();
		currentEntity.removeSomeArmies(1);
		findAssociatedButton(c).repaint();	//permet de forcer le repaint du countryButton...
		gamePhase1Tasks();					//...(vu que là c'est pas le joueur qui clique, il se repaint pas tout seul)
	}

	/**GAME LOOP (phase 2,3,4)*/

	//game loop : methode de controle du jeu qui sépare correctement les lancements de méthode en fonctino de la phase de jeu -> sera relancée à chaque changement de joueur
	public void gameLoop() {
		refreshOwnedCountries();
		if(gamePhase==2){
			gamePhase2Tasks() ;
		}else if(gamePhase==3){
			gamePhase3Tasks() ;
		}else if(gamePhase==4){
			gamePhase4Tasks() ;
		}
	}


	/**PHASE 2*/

	/*tâches de la phase 2 : cette phase est virtuelle, elle ne sera pas vue par le joueur (en tout cas actuellement)
	 *car elle initialise simplement le nombre des armées à déposer au début du tour du joueur -> dépend de plein de choses (nombre de continents possédés,
	 *combinaisons de cartes activées, etc.)
	 * */
	public void gamePhase2Tasks() { //Tâches effectuées lors du passage à la deuxième phase du jeu (initialisation du nombre d'armées)
		
		//Enregistrement des données du tour précédent.
		if(currentEntity.equals(entitiesList.getFirst())) {
			dataAvaibility=true ;
			rkDatas.register(entitiesList) ;
		}
		int armiesToGive = currentEntity.getOwnedCountries().size()/3;
		System.out.println("Armies to give beofre continent bonus" + armiesToGive);
		System.out.println(world.getWorldContinents());
		for(Continent continent : world.getWorldContinents()) {
			if(continent.isConqueredBy(currentEntity)) {
				System.out.println(continent+  "" + continent.getBonus());
				armiesToGive+=continent.getBonus();
			}
		}
		armiesToGive=Math.max(armiesToGive,3);	//même si le joueur n'a plus bcp de pays, il lui faut un minimum d'armées !
		System.out.println("Armies to give after bonus "+ armiesToGive);
		currentEntity.setArmiesInStock(armiesToGive) ;
		gamePhase=3 ;
		gameLoop() ;
	}


	/**PHASE 3*/

	//tâches de la phase 3 : tant que le joueur courant à des armées à déposer, on la relance (permet d'update le StepLabel notamment)
	public void gamePhase3Tasks() { //Tâches effectuées lors du passage à la troisième phase du jeu (le joueur courant place ses armées du tour)
		boolean nextStep=false ;
		if(currentEntity.getArmiesInStock()==0){
			nextStep=true ;
		}
		if(nextStep){
			gamePhase=4 ;
			gameLoop() ;
		}else{
			refreshOwnedCountries();
			messageToStepLabel(5);
			if(currentEntity.getToe().equals("AI1")) {
				Timer timer = new Timer() ;
				Task AI1TaskPhase3 = new Task(this,1,3) ; //AI number 1 and gamePhase nb 0.
				timer.schedule(AI1TaskPhase3, 1500);
			}
		}
	}

	public void gamePhase3AI1Tasks() {
		Country c = ((AI1)currentEntity).getCountryInPhase3();
		c.addArmies(currentEntity.getArmiesInStock());
		currentEntity.setArmiesInStock(0);
		findAssociatedButton(c).repaint();	//permet de forcer le repaint du countryButton...
		gamePhase3Tasks();					//...(vu que là c'est pas le joueur qui clique, il se repaint pas tout seul)
	}

	/**PHASE 4*/

	/*tâches de la phase 4 : phase d'attaque, cette méthode autorise à sélectionner les pays attaquants/défenseurs
	 *REMARQUE : cette methode n'est exécutée qu'une seule fois actuellement car c'est countryCliqued qui gère quand les pays sont cliqués
	 * Lorsque l'on devra coder l'IA pour cette phase, il faudra faire deux cas :
	 * si currentEntity est un joueur -> faire ce qui est codé ci-dessous ;
	 * si currentEntity est une IA -> désactiver la chooseStep et lancer l'algorithme de l'IA (à savoir : demander à l'IA les pays à faire combattre);
	 */
	public void gamePhase4Tasks() {
		chooseStep =true ;
		messageToStepLabel(4);
		if(currentEntity.getToe().equals("AI1")) {
			Timer timer = new Timer() ;
			Task AI1TaskPhase4 = new Task(this,1,4) ; //AI number 1 and gamePhase nb 0.
			timer.schedule(AI1TaskPhase4, 1500);
			((AI1)currentEntity).setNbLaunchAttack(0);	//reset le nb de lancer de dés
		}
	}

	//CODE A ECRIRE POUR LES TACHES DE L ATTAQUE PAR L IA
	//REMARQUE 1 : IL SERA PLUS FACILE DE CREER UNE AUTRE METHODE SPECIFIQUE A L'IA QUI PERMETTRA D ATTAQUER
	//L'IA FOURNIRA DIRECTEMENT UN PAYS ATTAQUANT ET UN PAYS DEFENSEUR, ET IL SERA PREVU DANS LE CODE DE L'IA QU'ELLE PUISSE AVOIR LE DROIT
	//CELA PERMETTRA D'EVITER DE REFAIRE DE NOMBREUSES CONDITIONS DE SELECTION DE PAYS INUTILES VU QUE L'IA LE FERA DEJA DANS SON ALGORITHME
	//REMARQUE 2 : NE PAS OUBLIER DE FAIRE REFRESH OWNED COUNTRIES A LA FIN DE L ATTAQUE PAR L IA ! (voir équivalent pour les joueurs réels dans RollingFrame)

	public void gamePhase4AI1Task() {
		LinkedList<Country> atkDefCountries = ((AI1)currentEntity).getAtkDefCountriesInPhase4();

		if(atkDefCountries.isEmpty()) {
			enterTheGameLoopAgain();
		} else {
			CountryButton cbAtk = findAssociatedButton(atkDefCountries.get(0));
			CountryButton cbDef = findAssociatedButton(atkDefCountries.get(1));
			cbAtk.setCircleAround(true);
			cbDef.setCircleAround(true);
			mainWindow.clearLinkedCountries();
			mainWindow.addLineBetween(cbAtk.getCountry(), cbDef.getCountry());
			mainWindow.drawLines();
			RollingFrame rf = new RollingFrame(cbAtk, cbDef, this);
			rf.roll();
			messageToStepLabel(3); //msg "is fighting"

		/*
		*définir le code de getAtkDefCountries() dans IA1
		*finir le code ici pour lancer l'attaque en s'inspirant de l'attaque lancée par un joueur
		*RQ : modifier la fenêtre qui s'ouvre pour qu'elle soit passive ? (on ne fait que voir les dés lancés par exemple)
		*IDEE POUR ARRETER LA PHASE D'ATTAQUE :
		* => si getAtkDefCountries renvoie un tableau vide, on considérera que l'IA ne veut plus attaquer, et on passe donc au tour suivant
		...
		 */
		}
	}


	public void enterTheGameLoopAgain() {
		if(gamePhase==4) {
			nextEntity(); //The next entity has to play
			gamePhase = 2; //We want to enter the loop at gamephase=2
			gameLoop(); //We call gameLoop() method which allow to run the implicit game loop.
		}
	}

    private void nextEntity() {
		if(entitiesList.indexOf(currentEntity)!=entitiesList.size()-1){
            currentEntity=entitiesList.get(entitiesList.indexOf(currentEntity)+1);
        } else {
            currentEntity=entitiesList.get(0);
        }
	}

	public void setChooseStep(boolean b){
		chooseStep = b ;
	}

	//méthode d'attaque pour le joueur
	private void countryTargetedInPhase4(Country targetedCountry) {
		Entity owner = targetedCountry.getOwner();
		if (attackingCountry==null && defenderCountry==null) {
			if(owner.equals(currentEntity)) {
				if(targetedCountry.getArmies()>1) {
					findAssociatedButton(targetedCountry).setCircleAround(true);
					attackingCountry = targetedCountry;
					showTheWays(attackingCountry);
					messageToStepLabel(1);
				} else {
					messageToStepLabel(0);
				}
			} else {
				findAssociatedButton(targetedCountry).setCircleAround(true);
				defenderCountry = targetedCountry ;
				showTheWays(defenderCountry);
				messageToStepLabel(2);
			}
		}

		else if (attackingCountry==null) {	//implicitement le pays défenseur n'est pas null
			if(owner.equals(currentEntity) ){	//si c'est un pays attaquant
				if(targetedCountry.getArmies()>1) {	//s'il a plus d'une armée
					if(defenderCountry.isNeighbor(targetedCountry)) {	//si le pays attaquant est voisin du pays défenseur, tout est validé et on lance l'attaque
						findAssociatedButton(targetedCountry).setCircleAround(true);	//on le sélectionne
						attackingCountry = targetedCountry;
						messageToStepLabel(3); //msg "is fighting"
						new RollingFrame(findAssociatedButton(attackingCountry), findAssociatedButton(defenderCountry), this);
						mainWindow.clearLinkedCountries();
						mainWindow.addLineBetween(attackingCountry, defenderCountry);
						attackingCountry = null ;
						defenderCountry = null ;
						chooseStep = false ;
					} else {	//si le pays choisi n'est pas voisin d'un pays defenseur déjà choisi
						findAssociatedButton(defenderCountry).setCircleAround(false);	//on désélectionne le pays défenseur préalablement choisi
						defenderCountry = null ;	// on enleve le pays defenseur choisi
						mainWindow.clearLinkedCountries();
						findAssociatedButton(targetedCountry).setCircleAround(true);
						attackingCountry = targetedCountry ;	//on mets le pays attaquant demandé
						showTheWays(attackingCountry);	//on montre les nouvelles possibilités d'attaque autour (suivant la meme methode que ci-dessus : on clear les countries et on ajoute une line pour chacun des pays à lier (le dessin se fait à la toute fin de la méthode)
						messageToStepLabel(1);
					}
				} else {	//le pays attaquant n'a pas assez d'armées
					messageToStepLabel(0);	//msg not enough armies to be able attack
				}
			} else {	//si c'est un pays défenseur
				if(defenderCountry.equals(targetedCountry)) {
					findAssociatedButton(defenderCountry).setCircleAround(false);	//on le déselectionne
					defenderCountry = null ;	//on l'enleve
					mainWindow.clearLinkedCountries();	//on enleve les pays liés
					messageToStepLabel(4);
				}
				else {	//si c'est un pays défenseur différent du premier
					findAssociatedButton(defenderCountry).setCircleAround(false);
					defenderCountry = null ;
					mainWindow.clearLinkedCountries();
					findAssociatedButton(targetedCountry).setCircleAround(true);	//on le sélectionne
					defenderCountry=targetedCountry;
					showTheWays(defenderCountry);
					messageToStepLabel(2);
				}
			}
		}

		else if (defenderCountry==null) {	//implicitement le pays attaquant n'est pas null
			if(!owner.equals(currentEntity) ){	//si c'est un pays defenseur
				if(attackingCountry.isNeighbor(targetedCountry)) {	//si le pays attaquant est voisin du pays défenseur, tout est validé et on lance l'attaque
					defenderCountry=targetedCountry;
					findAssociatedButton(targetedCountry).setCircleAround(true);	//on le sélectionne
					messageToStepLabel(3); //msg "is fighting"
					new RollingFrame(findAssociatedButton(attackingCountry), findAssociatedButton(defenderCountry), this);
					mainWindow.clearLinkedCountries();
					mainWindow.addLineBetween(attackingCountry, defenderCountry);
					attackingCountry = null ;
					defenderCountry = null ;
					chooseStep = false ;
				} else {	//si le pays defenseur choisi n'est pas voisin d'un pays attaquant déjà choisi
					findAssociatedButton(attackingCountry).setCircleAround(false);	//on désélectionne le pays attaquant préalablement choisi
					attackingCountry = null ;	// on enleve le pays attaquant choisi préalablement choisi
					mainWindow.clearLinkedCountries();
					findAssociatedButton(targetedCountry).setCircleAround(true);
					defenderCountry = targetedCountry ;	//on mets le pays defenseur demandé
					showTheWays(defenderCountry);	//on montre les nouvelles possibilités d'attaque autour (suivant la meme methode que ci-dessus : on clear les countries et on ajoute une line pour chacun des pays à lier (le dessin se fait à la toute fin de la méthode)
					messageToStepLabel(2);
				}
			} else {	//si c'est un pays attaquant
				if(attackingCountry.equals(targetedCountry)) {
					findAssociatedButton(attackingCountry).setCircleAround(false);    //on le déselectionne
					attackingCountry = null;    //on l'enleve
					mainWindow.clearLinkedCountries();    //on enleve les pays liés
					messageToStepLabel(4);	//choose what u want
				} else {	//si c'est un pays attaquant différent du premier
					if(targetedCountry.getArmies()>1) {	//et qu'il a effectivement plus d'une armée
						findAssociatedButton(attackingCountry).setCircleAround(false);
						attackingCountry = null ;
						mainWindow.clearLinkedCountries();
						findAssociatedButton(targetedCountry).setCircleAround(true);	//on le sélectionne
						attackingCountry=targetedCountry;
						showTheWays(attackingCountry);
						messageToStepLabel(1);
					} else {
						messageToStepLabel(0); 	//msg not enough armies to be able attack
					}
				}
			}
		}
		mainWindow.drawLines();
	}

	public void messageToStepLabel(int a) {
		//les messages à envoyer au stepLabel en fonction de quel message on veut

		String messageToGive;

		switch(a) {
			case 0 :
				messageToGive=", you are able to attack only if your country has more than 1 troop!";
				break;
			case 1 :
				messageToGive=", please choose the target country wou want to attack.";
				break;
			case 2 :
				messageToGive=", you can choose a country you will attack with.";
				break;
			case 3 :
				messageToGive=", is fighting. ";
				break;
			case 4 :
				messageToGive= ", please choose a country you want to attack or you want to use to attack.";
				break;
			case 5 :
				messageToGive= ", please place your armies for this turn ("+currentEntity.getArmiesInStock()+" armies to place).";
				break;

			default:
				messageToGive="Oops fail to give message";
		}
		mainWindow.updateStepLabel(currentEntity.toString()+messageToGive);
	}

	private void showTheWays(Country c){ //Méthode qui demande à mainWindow de montrer des chemins entre les pays. Les chemins possibles pour une bataille.
		LinkedList<Country> neighbors = c.getNeighborCountries() ;
		if(c.getOwner().equals(currentEntity)){ //CASE OF ATTACKING COUNTRY : si c'est l'attaquant qui est sélectionné.
			for(Country neighbor:neighbors){
				if(!neighbor.getOwner().equals(currentEntity)){ //Si l'attaquant est choisi en premier, on relie à tous les pays qu'il peut attaquer.
					mainWindow.addLineBetween(c,neighbor); // On montre les lignes qui ont la couleur de l'attaquant
				}
			}
		} else { //CASE OF DEFENDING COUNTRY : si c'est le défenseur qui est sélectionné en premier.
			for(Country neighbor:neighbors){
				if(neighbor.getOwner().equals(currentEntity) && neighbor.getArmies()>1){ //Si le défenseur est sélectionné avant l'attaquant, on relie tous les pays avec lesquels on peut attaquer.
					mainWindow.addLineBetween(neighbor,c); //// On montre les lignes qui ont la couleur de l'attaquant
				}
			}
		}
	}

	public void refreshOwnedCountries() {
		for(Entity e : entitiesList) {
			LinkedList<Country> ownedCountries = new LinkedList<>();
			for(Country c : countries) {
				if (c.getOwner().equals(e)) {
					ownedCountries.add(c);
				}
			}
			e.setOwnedCountries(ownedCountries);
			//si un des joueurs possède 24 territoires, il gagne la partie
			if(e.getOwnedCountries().size()>=24) {
				endOfTheGame();
			}
		}
	}

	public void refreshPlayers() {
		for(Entity e : entitiesList) {
			if(e.getOwnedCountries().isEmpty()) {
				entitiesList.remove(e);
				System.out.println(e + "a été éliminé ! ");
			}
		}
		//s'il ne reste qu'un seul joueur, alors il gagne
		if(entitiesList.size()==1) {
			endOfTheGame();
		}
	}

	public void attackEnd() {
		mainWindow.clearLinkedCountries();
		this.setChooseStep(true);
		this.messageToStepLabel(4);
		this.refreshOwnedCountries();
		this.refreshPlayers();
	}

	private void endOfTheGame() {
		this.getMainWindow().dispose();
		System.out.println("LE GAGNANT EST :" + entitiesList);
		System.exit(0);	//à modifier, actuellement ca permet simplement d'arreter le programme quand le jeu est fini afin d'éviter que l'IA continu de tourner derriere
		//d'ailleurs vaudrait mieux faire tourner certain bouts de codes dans un thread ce qui permettra de le faire s'interrompre car actuellement on peut faire arrêter une tâche
		//genre si je veux faire arrêter le jeu, je veux juste que Sync s'arrête, et que ce soit autre chose qui prenne le relai
		//par exemple on fait une new Frame avec dedans un petit message avec deux boutons "Quitter" / "Retour au menu" ou bien les stats si on veut
		//et ensuite on arrête le thread de Sync :)
	}
}
