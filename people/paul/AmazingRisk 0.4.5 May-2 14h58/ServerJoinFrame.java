import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerJoinFrame extends JFrame {

    private boolean isGameJoined = false ;

    private JTextField ipField;
    private JTextField portField;
    private JTextField playerNameField;

    public ServerJoinFrame() {
        super(TitleOf.ServerJoinFrame);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,800);
        setLocationRelativeTo(null);
        setVisible(true);

        //CENTER ORGANISATION
        JPanel mainPanel = new JPanel();
        JPanel gridMainPanel = new JPanel(new GridLayout(2, 1, 4,4));
        JPanel serverInfoPanel = new JPanel();
        JPanel playerInfoPanel = new JPanel();

        JLabel ipLabel = new JLabel("Server IP : ");
        ipField = new JTextField("127.0.0.1");
        JLabel portLabel = new JLabel("Server Port : ") ;
        portField = new JTextField("666");
        JLabel playerNameLabel = new JLabel("Player Name : ");
        playerNameField = new JTextField("Player "+ (int)(Math.random()*10));

        serverInfoPanel.add(ipLabel);
        serverInfoPanel.add(ipField);
        serverInfoPanel.add(portLabel);
        serverInfoPanel.add(portField);

        playerInfoPanel.add(playerNameLabel);
        playerInfoPanel.add(playerNameField);

        gridMainPanel.add(serverInfoPanel);
        gridMainPanel.add(playerInfoPanel);

        mainPanel.add(gridMainPanel);
        this.add(mainPanel, BorderLayout.CENTER);

        //SOUTH ORGANISATION
        JPanel southPanel = new JPanel();
        AmazingButton joinBtn = new AmazingButton("Join server!") ;
        joinBtn.addActionListener(new JoinOnlineGameListener(this));
        southPanel.add(joinBtn);
        this.add(southPanel,BorderLayout.SOUTH);
    }

    public void joinServer() throws UnknownHostException {
        InetAddress serverIP = InetAddress.getByName(ipField.getText());
        int serverPort = Integer.parseInt(portField.getText());
        String clientName = playerNameField.getText();
        new GreetingClient(serverIP, serverPort, clientName, false);
        isGameJoined=true;
        this.dispose();
    }

    public void dispose() {
        if(!isGameJoined) {
            new OnlineFrame();
        }
        super.dispose();
    }

}
