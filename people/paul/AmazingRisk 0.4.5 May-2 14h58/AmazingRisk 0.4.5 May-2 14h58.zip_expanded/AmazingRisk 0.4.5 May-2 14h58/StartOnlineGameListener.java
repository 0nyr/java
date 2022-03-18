import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartOnlineGameListener implements ActionListener {
    private PlayerServerMenu psm;

    public StartOnlineGameListener(PlayerServerMenu psm) {
        this.psm =psm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        psm.startOnlineGame();
    }
}
