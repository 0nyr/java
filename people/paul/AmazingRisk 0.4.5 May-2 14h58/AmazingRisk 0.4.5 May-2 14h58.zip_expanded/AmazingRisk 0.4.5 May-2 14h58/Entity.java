import java.util.LinkedList;

public class Entity {
	
	private String name , attributedColour, toe;
	private int armiesInStock ;
	protected LinkedList<Country> ownedCountries;
	private LinkedList<Integer> armiesPerTurnList, countriesPerTurnList ;

	public Entity(String name, String toe, String attributedColour) {
		this.name=name ;
		this.toe=toe;
		this.attributedColour=attributedColour ;
		this.armiesPerTurnList = new LinkedList<>() ;
		this.countriesPerTurnList = new LinkedList<>() ;
		armiesPerTurnList.add(0) ;
		countriesPerTurnList.add(0) ;
	}

	/**GETTERS
	 * */
	public String getName() {
		return name;		//return the name of the player/AI
	}

	public String getAttributedColour() {
		return attributedColour;	//return the attributedColour of the player/AI
	}

	public String getToe() {
		return toe;		//return the type of entity (Player or AI)
	}

	public int getArmiesInStock() {
		return armiesInStock ;
	}

	public LinkedList<Country> getOwnedCountries() {
		return ownedCountries;
	}
	
	public LinkedList<Integer> getArmiesDatas() {
		return armiesPerTurnList ;
	}
	public LinkedList<Integer> getCountriesDatas() {
		return countriesPerTurnList ;
	}

	public String toString() {
		return name +" ("+attributedColour+" "+toe+")" ;
	}

	/**SETTERS*/

	public void setOwnedCountries(LinkedList<Country> ownedCountries){		//Sync lui donnera la liste des pays qu'il possède
		this.ownedCountries=ownedCountries;
	}

	public void setArmiesInStock(int armies){
		armiesInStock=armies ;
	}

	public void removeSomeArmies(int armies) {
		armiesInStock-=armies;
	}
	
	public void addDatas(int nbOfArmiesForThisTurn, int nbOfCountriesForThisTurn){
		countriesPerTurnList.add(nbOfCountriesForThisTurn) ;
		armiesPerTurnList.add(nbOfArmiesForThisTurn) ;
	}
	
	

}
