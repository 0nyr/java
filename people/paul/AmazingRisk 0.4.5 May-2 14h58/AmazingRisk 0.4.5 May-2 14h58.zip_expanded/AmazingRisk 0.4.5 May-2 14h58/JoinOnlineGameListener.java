import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

public class JoinOnlineGameListener implements ActionListener {

    private ServerJoinFrame sjf;

    public JoinOnlineGameListener(ServerJoinFrame sjf) {
        this.sjf=sjf;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            sjf.joinServer();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }
}
