import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedList;

public class ServerCreationFrame extends JFrame {

    LinkedList<ImageIcon> maps = Maps.getMaps();
    private int currentMap;
    private boolean gameIsReadyToStart = false ;

    private JLabel selectedMap;
    private JTextField creatorNameField;
    JComboBox<Integer> maxPlayersComboBox;


    public ServerCreationFrame(){
        super(TitleOf.ServerCreationFrame) ;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,800);
        setLocationRelativeTo(null);
        setVisible(true);


        //NORTH ORGANIZATION
        JPanel gridnorthwestpanel= new JPanel(new GridLayout(2,1)) ;
        JPanel northPanel= new JPanel() ;
        JPanel panelOfMapChoices=new JPanel() ;
        JLabel mapToChoose= new JLabel("Please choose a map : ") ;
        selectedMap=new JLabel("< No selected map >") ;

        for (ImageIcon map : maps) {
            double coeff = (double) (map.getIconWidth()) / map.getIconHeight();
            int height = 100;
            int width = (int) (coeff * 100.0);
            String btnDescription = map.getDescription();
            int debut = btnDescription.indexOf("/m");
            int fin = btnDescription.indexOf(".jpg", debut);
            btnDescription = btnDescription.substring(debut + 1, fin);
            IconButton btn = new IconButton(btnDescription, map, width, height);
            btn.addActionListener(new MapChoiceListener(this, btn));
            btn.setToolTipText(btnDescription) ;
            btn.setPreferredSize(new Dimension(width, height));
            panelOfMapChoices.add(btn);
        }
        gridnorthwestpanel.add(mapToChoose) ;
        gridnorthwestpanel.add(selectedMap) ;
        northPanel.add(gridnorthwestpanel) ;
        northPanel.add(panelOfMapChoices) ;
        this.add(northPanel, BorderLayout.NORTH) ;
        add(new JScrollPane(northPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.NORTH) ;


        //CENTER ORGANIZATION
        JLabel maxPlayersLabel = new JLabel("Maximum number of players : ") ;
        maxPlayersComboBox = new JComboBox<>(new Integer[]{2,3,4,5,6}) ;
        JPanel maxPlayersPanel = new JPanel();
        maxPlayersPanel.add(maxPlayersLabel);
        maxPlayersPanel.add(maxPlayersComboBox);

        JLabel creatorNameLabel = new JLabel("Creator's name : ") ;
        creatorNameField = new JTextField("GameMaster");
        JPanel creatorNamePanel = new JPanel();
        creatorNamePanel.add(creatorNameLabel);
        creatorNamePanel.add(creatorNameField);

        JPanel gridCenterPanel = new JPanel(new GridLayout(2,1));
        gridCenterPanel.add(maxPlayersPanel);
        gridCenterPanel.add(creatorNamePanel);
        JPanel flowCenterPanel = new JPanel();
        flowCenterPanel.add(gridCenterPanel);
        this.add(flowCenterPanel, BorderLayout.CENTER);

        //SOUTH ORGANIZATION
        AmazingButton startBtn=new AmazingButton("Create a server");
        startBtn.setToolTipText("Click if you are ready to start") ;
        startBtn.addActionListener(new LocalStartListener(this)) ;
        JPanel southPanelForStart=new JPanel(new FlowLayout()) ;
        southPanelForStart.add(startBtn) ;
        this.add(southPanelForStart, BorderLayout.SOUTH) ;

    }

    public void setChosenMap(String mapName) {
        selectedMap.setText("< "+mapName+" >") ;
        int debut = mapName.indexOf("_") + 1;
        currentMap=Integer.parseInt(mapName.substring(debut));
        validate(); repaint();
    }


    public void createServerGame() {
        int port = 666;
        int maxNbOfPlayers = (int)maxPlayersComboBox.getSelectedItem();
        String clientName= creatorNameField.getText();
        try {
            InetAddress ip = InetAddress.getLocalHost();
            CnxServer server = new CnxServer(port, maxNbOfPlayers, currentMap);
            server.start();
            GreetingClient gt = new GreetingClient(ip, port, clientName, true);
            gameIsReadyToStart=true;
            this.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dispose() {
        if(!gameIsReadyToStart) {
            new OnlineFrame();
            maps.clear();   //permet de ne pas faire de map en doublon si l'on a fermé puis rouvert la ServerCreationFrame
        }
        super.dispose();
    }
}
