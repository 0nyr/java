import java.util.LinkedList;

public class Country {

	private String countryName ;
	private Continent continent ;
	private Entity owner ; //The owners of the country
	private double coeffX, coeffY ;
	private int armies = 0 ;
	protected Sync sync ;
	private LinkedList<Country> neighborCountries;
	
	public Country(Continent continent, String countryName, double coeffX, double coeffY) {
		this.countryName=countryName ;
		this.continent=continent ;
		this.owner=null ; //No owner when the country is created
		this.coeffX=coeffX ; //Geographical location
		this.coeffY=coeffY ;

	}

	/**GETTERS*/

	public Continent getContinent() {
		return continent;
	}
	public String getCountryName(){
		return countryName ;
	}
	
	public Entity getOwner() {	return owner;	}
	public double getCoeffX(){
		return coeffX ;
	}
	public double getCoeffY(){
		return coeffY ;
	}
	public int getArmies(){
		return armies ;
	}
	public LinkedList<Country> getNeighborCountries() {
		return neighborCountries;
	}
	public LinkedList<Country> getNeighborEnnemies() {
		LinkedList<Country> neighborEnnemies = new LinkedList<>() ;
		for(Country neighbor : neighborCountries) {
			if(!neighbor.getOwner().equals(owner)) {	//si l'un des pays voisins n'appartient à celui
				neighborEnnemies.add(neighbor);
			}
		}
		return neighborEnnemies;
	}

	/**SETTERS*/
	public void setName(String newName){ //méthode de renommage
		countryName=newName ;
	}

	public void setOwner(Entity newOwner){
		owner=newOwner ;
	}
	public void addArmies() {
		armies++;
	}
	public void addArmies(int armies) {this.armies+=armies ;}
	public void setArmies(int armies) {
		this.armies=armies ;
	}
	public void setNeighborCountries(LinkedList<Country> neighborList){
		this.neighborCountries=neighborList ;
	}



	public void isClicked() {
		sync.countryClicked(this);
	}

	public void addSync(Sync sync){
		this.sync=sync ;
	}

	public boolean isNeighbor(Country countryChosen){
		boolean isNeighbor = false ;
		for (Country c: neighborCountries){
			if (c.equals(countryChosen)){
				isNeighbor= true;
				break;
			}
		}
		return isNeighbor ;
	}

	public String toString(){
		return countryName+" is in "+continent+" and is located in the geographical point ("+coeffX+";"+coeffY+")." ; 
	}

}
