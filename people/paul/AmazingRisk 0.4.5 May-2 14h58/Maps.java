import javax.swing.*;
import java.util.*;
import java.util.LinkedList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//Cette classe sert à stocker et à fournir les différentes cartes pour jouer
//Les maps sont stockées dans une LinkedList.
//Dans une autre autre LinkedList, il y a une LinkedList qui stockera les coordonnées de chaque bouton correspondant à chaque map.
//Le tableau de double contiendrait le coefficient de X et de Y.

//La question est : Doit-on lire les coordonnées à partir de fichiers extérieurs ou de cette même classe, on stockera dans un tableau le cas échéant).

public class Maps{

	private static LinkedList<ImageIcon> maps = new LinkedList<>();
	static int MAX_NB_OF_MAPS = 100;

	//Methode utilisée uniquement dans Initializer afin d'afficher les maps disponibles
	public static LinkedList<ImageIcon> getMaps(){
		for(int w = 1; w<=MAX_NB_OF_MAPS; w++){

			ImageIcon img = new ImageIcon(("maps/map_"+w+".jpg")) ; //It is possible to add 100 different maps.
			if(img.getIconWidth()>0){ //But I want to add only the existing maps (not empty instances of ImageIcon). So we verify that the ImageIcon has a width.
				maps.add(img) ;
			}
		}

		return maps ;
	}


	/*Cette méthode crée le World associé à la map voulue, qui contient tous les continents et les pays
	*Elle est utilisée uniquement dans le constructeur de Sync (qui donne les countries issus de World à MainWindow)
	* */
	public static World getWorld(int numOfTheMap) throws IOException {
		World world = new World() ;
		LinkedList<Continent> continents = new LinkedList<>() ;
		LinkedList<Country> countries=new LinkedList<>() ;

		Continent continent=null;
		String countryName;
		double coeffX;
		double coeffY;

		for (String line : Files.readAllLines(Paths.get("maps/map" + numOfTheMap + "countries.txt"))) {
			LinkedList<String> theLine;
			theLine = new LinkedList<>();
			Collections.addAll(theLine, line.split(","));

			Iterator<Continent> it = continents.iterator();
			String name = theLine.get(0) ;

			boolean isNew=true;
			while (it.hasNext()) {
				Continent c = it.next();
				if (c.getName().equalsIgnoreCase(name)) {
					continent = c;
					isNew = false;
					break;
				}
			}
			if (isNew) {
				continent = new Continent(name);
				continents.add(continent);
			}

				/*for (Continent c : continents) {
					if(c.getName().equalsIgnoreCase(continent.getName())) {
						continents.add(continent);
					} else {
						continent = c;
					}

				}
			} else { continents.add(continent);}*/

			countryName=theLine.get(1) ;
			coeffX=Double.parseDouble(theLine.get(2)) ;
			coeffY=Double.parseDouble(theLine.get(3)) ;


			Country c = new Country(continent, countryName,coeffX,coeffY);
			continent.add(c);
			countries.add(c);


			//countries.add(new Country(continent, countryName,coeffX,coeffY)) ; (already done earlier by @Manal Saoui)

		}

		//Ajout de la liste des pays voisins au country concerné (obligé de refaire un parcours total, une fois que tous les pays sont créés
		int i=0;	//permet de donner la liste des pays voisins au bon pays
		for (String line : Files.readAllLines(Paths.get("maps/map" + numOfTheMap + "countries.txt"))) {

			Country country = countries.get(i);    //pays correspondant à la ligne lu

			LinkedList<String> theLine;
			theLine = new LinkedList<>();
			Collections.addAll(theLine, line.split(","));

			//permet de récupérer les pays voisins du pays situé sur la ligne
			int d = theLine.size() - 4;
			LinkedList<Country> neighborList = new LinkedList<>();
			for (int a = 4; a < theLine.size(); a++) {    //on regarde ce qu'il se passe à partir de "l'espace 4" de la ligne (défini par Collections.add(theLine, lineSPlit...))
				for (Country c : countries) {    //on parcourt la liste des pays et on regarde lequel a le nom du voisin que l'on veut
					if (c.getCountryName().equals(theLine.get(a))) {    //et on regarde lequel a le nom du voisin que l'on veut
						neighborList.add(c) ;    //quand c'est le bon, on l'ajoute à la liste des pays voisins
					}
				}
			}
			country.setNeighborCountries(neighborList);
			i++;
		}

		for(Continent c : continents) {
			c.setBonus();
			world.add(c);
		}
		return world ;
	}


	/*Cette méthode donne la map voulue
	* Elle est utilisée uniquement dans le constructeur de Sync (qui donne l'ImageIcon à MainWindow)
	* */
	public static ImageIcon getMap(int i){
		return maps.get(i-1) ;
	}
}
