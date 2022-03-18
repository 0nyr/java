import java.util.LinkedList;


public class RankingDatas {
	
	protected LinkedList<Entity> allEntitiesList ;
	private LinkedList<Country> allCountriesList ;
	
	public RankingDatas(LinkedList<Entity> allEntitiesList,LinkedList<Country> allCountriesList){
		this.allEntitiesList=allEntitiesList ;
		this.allCountriesList=allCountriesList ;
	}
	
	protected void register(LinkedList<Entity> entitiesInThisTurnList){
		
		for(Entity entityInTheGame : allEntitiesList) {
			for(Entity entityInThisTurn : entitiesInThisTurnList) {
				if(entityInThisTurn.equals(entityInTheGame)){
					entityInThisTurn.addDatas(countingArmiesOf(entityInThisTurn),countingCountriesOf(entityInThisTurn)) ;
				}
			}
		}
		
	}
	
	private int countingCountriesOf(Entity entity){
		int nbOfCountries=0 ;
		
		for(Country country : allCountriesList){
			if(entity.equals(country.getOwner())){
				nbOfCountries++ ;
			}
		}
		return nbOfCountries ;
	}
	
	private int countingArmiesOf(Entity entity){
		int nbOfArmies=0 ;
		
		for(Country country : allCountriesList){
			if(entity.equals(country.getOwner())){
				nbOfArmies=nbOfArmies+country.getArmies() ;
			}
		}
		return nbOfArmies ;
	}
				

}
