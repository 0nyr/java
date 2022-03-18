import java.util.LinkedList;
public class AI1 extends Entity {

	private Sync sync;
	private LinkedList<Country> countriesMemoryPhase1 = new LinkedList<>();	//liste des pays qu'il faut encore renforcer dans la phase 1
	private Country countryMemoryPhase3;	//pays renforcé en phase 3 (renforcement) à réutiliser pour l'attaque en phase 4
	private int nbLaunchAttack=0;	//parametre permettant de limiter le nombre d'attaque de de l'IA lors d'un tour

    public AI1(String name, String attributedColour) {
	    super(name, "AI1", attributedColour);
	}
	public void addSync(Sync sync) {
		this.sync=sync;
	}	//necessaire car les IA sont créées à la fin de l'initializer, alors que Sync n'existe pas encore


	public Country getCountryInPhase0() {        //AI chooses the first country that doesn't have an owner
		Country a ;
		LinkedList<Country> cTmpList = new LinkedList<>(sync.getCountries());
		do{
			a = cTmpList.get((int)(Math.random()*cTmpList.size()));	//gourmand en calcul mais je n'arrive pas à copier la liste avec ses références afin de ne modifier que l'intérieur (pas la liste en elle meme)
		} while (a.getOwner()!=null);

		return a;
	}

	//on renforce les pays un par un et s'il reste encore des armées à placer alors que tous les pays ont déjà été remplies,
	//on repart au début
	public Country getCountryInPhase1() {
    	if(countriesMemoryPhase1.isEmpty()) {
    		countriesMemoryPhase1 = new LinkedList<>(ownedCountries);
    	}
    	Country toReturn ;
    	toReturn = countriesMemoryPhase1.get(0);
    	countriesMemoryPhase1.remove(toReturn);
    	return toReturn;
	}

	//on récupère un pays aléatoire à renforcer, mais de manière à ce qu'il puisse attaquer pendant le tour (vérification qu'il a des pays ennemis voisins)
	public Country getCountryInPhase3() {
		do{
			int i = (int)(Math.random()*ownedCountries.size());
			countryMemoryPhase3=ownedCountries.get(i);
		}while(countryMemoryPhase3.getNeighborEnnemies().isEmpty());

		return countryMemoryPhase3;
	}

	//permet de remettre le compteur des attaques lancées à 0 lorsque le tour débute
	public void setNbLaunchAttack(int nbLaunchAttack) {
		this.nbLaunchAttack = nbLaunchAttack;
	}

	//methode d'attaque
	//RQ : si l'on n'ajoute pas les pays à la liste (nombre d'attaques suffisant ou plus assez d'armées pour attaquer)
	//alors on renvoie une liste vide, qui est interprétée par Sync comme étant la fin du tour
	public LinkedList<Country> getAtkDefCountriesInPhase4() {
		LinkedList<Country> countriesAtkDef = new LinkedList<>();
		if(nbLaunchAttack<=3) {
			Country def;
			Country att = countryMemoryPhase3;

			if(att.getArmies()>1){
				def=(att.getNeighborEnnemies()).get((int)(Math.random())*(att.getNeighborEnnemies()).size());
				countriesAtkDef.add(att);
				countriesAtkDef.add(def);
				nbLaunchAttack++;
			}
		}

		return countriesAtkDef ;
	}


}
