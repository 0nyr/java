import java.util.LinkedList;

public class World {

    private LinkedList<Continent> worldContinents = new LinkedList<>() ;

    public World () {}

    public LinkedList<Continent> getWorldContinents() {
        return worldContinents;
    }

    public void add(Continent continent) {
        worldContinents.add(continent);
    }

    public LinkedList<Country> getAllCountries() {
        LinkedList<Country> countries = new LinkedList<>() ;
        for(Continent continent : worldContinents) {
            LinkedList<Country> continentCountries = continent.getContinentCountries() ;
            countries.addAll(continentCountries);
        }
        return countries ;
    }
}

//INFO : CETTE CLASSE EST UTILE POUR LA CLARIFICATION DES CHOSES ET PERMET DE REUNIR TOUT EN UNE CLASSE
//World contient les continents ; chaque Continent contient les pays