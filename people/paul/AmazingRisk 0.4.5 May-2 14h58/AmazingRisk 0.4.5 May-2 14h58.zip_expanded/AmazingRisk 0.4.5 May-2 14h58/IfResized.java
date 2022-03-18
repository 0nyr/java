import java.awt.event.*;
import java.awt.event.ComponentEvent;

public class IfResized implements ComponentListener {
	
    private MainWindow window ;

    public IfResized(MainWindow window){
        this.window=window ;
    }

    public void componentResized(ComponentEvent e){
        window.resizePanel() ;
    }
    public void componentHidden(ComponentEvent e){
    }
    public void componentShown(ComponentEvent e){
    }
    public void componentMoved(ComponentEvent e){
    }
}
