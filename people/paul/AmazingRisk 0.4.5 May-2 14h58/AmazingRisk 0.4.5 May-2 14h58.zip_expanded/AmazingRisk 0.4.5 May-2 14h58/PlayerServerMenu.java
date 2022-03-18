import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Objects;

public class PlayerServerMenu extends JFrame {

    private Client c;
    private String playerName;
    private boolean isCreator ;
    private String color;

    //Ne sert quà la transmission des données au serveur
    protected GreetingClient gc;

    private LinkedList<Client> listeClients = new LinkedList<>();

    private int nbMaxOfPlayers;
    private JPanel gridCenterPanel;
    private JComboBox<String> colorComboBox;

    public PlayerServerMenu(int mapChosen, int nbMaxOfPlayers, Client c, GreetingClient gc) {
        super(TitleOf.PlayerServerMenu);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200,800);
        setLocationRelativeTo(null);
        setVisible(true);

        this.nbMaxOfPlayers=nbMaxOfPlayers;

        this.c = c;
        this.isCreator=c.isCreator;
        this.playerName=c.playerName;

        //NORTH ORGANISATION
        ImageIcon map = new ImageIcon("maps/map_"+mapChosen+".jpg");
        double coeff = (double) (map.getIconWidth()) / map.getIconHeight();
        int height = 200;
        int width = (int) (coeff * height);
        IconButton mapButton = new IconButton("",map, width, height);
        mapButton.setEnabled(false);
        JPanel northPanel = new JPanel();
        northPanel.add(mapButton);
        this.add(northPanel, BorderLayout.NORTH);

        //CENTER ORGANISATION
        JPanel flowCenterPanel = new JPanel();
        gridCenterPanel = new JPanel(new GridLayout(nbMaxOfPlayers,1, 4, 4));
        this.addOwnerLine();
        flowCenterPanel.add(gridCenterPanel);
        this.add(flowCenterPanel, BorderLayout.CENTER);

        //SOUTH ORGANISATION
        if(isCreator) {
            AmazingButton startButton = new AmazingButton("START");
            startButton.addActionListener(new StartOnlineGameListener(this));
            JPanel southPanel = new JPanel();
            southPanel.add(startButton);
            this.add(southPanel, BorderLayout.SOUTH);
        }
    }

    public void startOnlineGame() {
        //NE PAS OUBLIER DE DEMANDER LA FERMETURE DU SERVEUR LORSQUE LE JEU DEMARRE
    }

    //cette méthode enlève les lignes des autres clients, et conserve l'actuelle
    //le soucis étant que si je veux changer les infos des clients, il faudrait que cette fenetre connaisse aussi tous les clients, ce qui ferait trop de stockage
    //donc c'est le serveur qui gère la liste des clients, et s'il y a une info qui change sur un de ces clients, alors il renvoie la liste à tous
    public void removeAllPlayerLines() {
        for(int i=1; i<nbMaxOfPlayers ; i++) {
            gridCenterPanel.remove(i);
        }
    }

    //methode de creation de la ligne du greetingclient sur machine
    private void addOwnerLine() {
        JPanel flowCenterPanel = new JPanel();
        JLabel playerNameLabel = new JLabel("Name : " + playerName);
        flowCenterPanel.add(playerNameLabel);

        JPanel colourPanel = new JPanel();
        JLabel colourLabel = new JLabel("Colour : ");
        colorComboBox = new JComboBox<>(new String[] {"Select...","Blue","Green","Red","Yellow","Magenta", "Cyan","Pink","White","Gray","Orange"});
//        colorComboBox.addActionListener(new ColourChoiceListener(this, colorComboBox));
        colourPanel.add(colourLabel) ; colourPanel.add(colorComboBox);
        flowCenterPanel.add(colourPanel);

        if(isCreator) {
            ImageIcon creatorstar = new ImageIcon("icons/creatorstar.png") ;
            JLabel starCreatorLabel = new JLabel() {
                @Override
                public void paintComponent (Graphics g) {
                    super.paintComponent (g);
                    g.drawImage (creatorstar.getImage(), 0, 0, 100 ,100, null);
                }
            };
            flowCenterPanel.add(starCreatorLabel);
        }
        gridCenterPanel.add(flowCenterPanel);
        validate();
        repaint();
    }

    //méthode accédée depuis l'extérieur pour ajouter les informations des autres clients
    public void addClientLine(Client c) {
        JPanel flowCenterPanel = new JPanel();
        JLabel playerNameLabel = new JLabel("Name : " + playerName);
        flowCenterPanel.add(playerNameLabel);

        JPanel colourPanel = new JPanel();
        JLabel colourCommonLabel = new JLabel("Colour : ");
        JLabel colourLabel = new JLabel(c.color);
        colourLabel.setForeground(Color.getColor(c.color));
        colourPanel.add(colourCommonLabel) ; colourCommonLabel.add(colourCommonLabel);
        flowCenterPanel.add(colourPanel);

        if(c.isCreator) {
            ImageIcon creatorstar = new ImageIcon("icons/creatorstar.png") ;
            JLabel starCreatorLabel = new JLabel() {
                @Override
                public void paintComponent (Graphics g) {
                    super.paintComponent (g);
                    g.drawImage (creatorstar.getImage(), 0, 0, 100 ,100, null);
                }
            };
            flowCenterPanel.add(starCreatorLabel);
        }
        gridCenterPanel.add(flowCenterPanel);
        validate();
        repaint();
    }

    public void updateDisplay(LinkedList<Client> listeClients) {
        this.removeAllPlayerLines();
        for(Client c : listeClients) {
            if(!this.c.equals(c)) {
                this.addClientLine(c);
            }
        }
    }

    //methode permettant de changer la couleur de fond du JComboBox et d'envoyer l'information comme quoi la couleur a changé au serveur
    public void setClientColour() {
        color= (String) colorComboBox.getSelectedItem();
        colorComboBox.setBackground(ColorFactory.getColorFromString(Objects.requireNonNull(color)));
        gc.color =color;
        gc.sendDataElt(new PlayerServerMenuEditor(gc));
    }

}
