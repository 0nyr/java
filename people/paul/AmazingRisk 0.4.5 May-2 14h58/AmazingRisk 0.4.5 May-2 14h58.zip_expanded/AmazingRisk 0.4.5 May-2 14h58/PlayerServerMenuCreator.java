import java.util.LinkedList;

//Cet objet envoyé par le ClientManager permet au GreetingClient de créer sa Frame où seront affichées les joueurs
//Elle est volontairement séparée de l'objet éditeur de Frame afin que la fenêtre soit créée propre au GreetingClient (il sera le seul à avoir des droits pour changer sa couleur par exemple)

public class PlayerServerMenuCreator extends DataElt {

    private int maxNbOfPlayers, chosenMap;
    private Client c;

    public PlayerServerMenuCreator(int maxNbOfPlayers, int chosenMap, Client c) {
        super();
        this.maxNbOfPlayers=maxNbOfPlayers;
        this.chosenMap=chosenMap;
        this.c=c;
    }


    public PlayerServerMenu createPlayerServerMenu(GreetingClient gc) {
        return new PlayerServerMenu(chosenMap,maxNbOfPlayers, c, gc);
    }

    @Override
    public String toString() {
        return "OnlinePlayerFrame{}";
    }
}
