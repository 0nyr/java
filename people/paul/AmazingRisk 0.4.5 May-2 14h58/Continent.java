import java.util.LinkedList;

public class Continent {
    private String name ;
    private LinkedList<Country> continentCountries = new LinkedList<>() ;
    private int bonus = 0 ;

    public Continent (String name) {
        this.name =name ;
    }

    public void add(Country c) {
        continentCountries.add(c);
    }

    public void setBonus() {
        int nbCountries = continentCountries.size();
        if(nbCountries>0) {
            if(nbCountries<=4) {
                bonus=2;
            } else if (nbCountries<=6) {
                bonus=3;
            } else if(nbCountries<=9) {
                bonus=5;
            } else { bonus=7; }
        } else { System.out.println("Error : the continent has " + nbCountries + "countries."); }
    }

    public String getName() {
        return name;
    }

    public LinkedList<Country> getContinentCountries() {
        return continentCountries ;
    }

    public LinkedList<Country> getCountriesOfEntity(Entity e) {
        LinkedList<Country> countriesOfEntity = new LinkedList<>() ;
        for (Country c: continentCountries) {
            if (c.getOwner().equals(e)) {
                countriesOfEntity.add(c);
            }
        }
        return countriesOfEntity ;
    }

    public int getBonus() {
        return bonus;
    }

    public boolean isConqueredBy(Entity e) {
        boolean isConquered = true ;
        for (Country c : continentCountries) {
            if(!c.getOwner().equals(e)) {
                isConquered = false ;
                break;
            }
        }
        return isConquered ;
    }


    public String toString() {
        return name ;
    }
}
