import java.util.LinkedList;

public class PlayerServerMenuEditor extends DataElt {

    private LinkedList<Client> listeClients;
    protected GreetingClient gc;

    //Constructeur pour le serveur, qui envoie la liste des clients à tout le monde pour pouvoir s'actualiser avec les bonnes infos
    public PlayerServerMenuEditor(LinkedList<Client> listeClients) {
        this.listeClients=new LinkedList<>(listeClients);
    }

    //Constructeur pour un GreetingClient qui envoie une donnée à modifier (on n'aura que la couleur de modifiable)
    public PlayerServerMenuEditor(GreetingClient gc) {
        this.gc=gc;
    }

    public void edit(PlayerServerMenu psm) {
        psm.removeAllPlayerLines();
        GreetingClient gc = psm.gc;
        psm.updateDisplay(listeClients);
    }

    public void setClientColor(Client c) {
        c.color = gc.color;
    }

}
