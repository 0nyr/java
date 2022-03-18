//ADAPTED FROM A WEB-JAVA-FORUM.
import java.awt.*;
import java.util.Timer;
 
public class SplashScreen extends Frame {
 
  protected Image splashImage;
  protected static SplashScreen sp;
  private Frame f ;
 
  public SplashScreen() throws HeadlessException {
    super();
    setSize(400,300);
    setUndecorated(true);
    setFocusable(false);
    setEnabled(false);
    splashImage = getToolkit().createImage("icons/SplashAR2.png");
    try
    {
       MediaTracker mTrack = new MediaTracker( this );
       mTrack.addImage(splashImage, 1);
       mTrack.waitForAll();
    } catch( Exception e ) {
       e.printStackTrace();
    }
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  }
 
  public void paint( Graphics g )
   {
       super.paint( g );
       Dimension d = this.getSize();
       g.drawImage(splashImage, 0, 0, d.width, d.height, this );
   }
 
   public void openSplashScreen(int seconds) {
     try {
		 
		
       GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
       GraphicsDevice[] gs = ge.getScreenDevices();
       GraphicsDevice gd = gs[0];
       GraphicsConfiguration[] gc = gd.getConfigurations();
       Rectangle r = gc[0].getBounds();
       Point pt = new Point(  r.width / 2, r.height / 2);
       sp = new SplashScreen();
       Point loc = new Point(pt.x - 200, pt.y - 150);
       sp.setLocation(loc);
       sp.setVisible(true);
       
       Timer timer = new Timer() ;
       Task myTask=new Task(this) ;
       f=new AmazingMenu() ;
       timer.schedule(myTask, seconds*1000);
       
       
       
       
     }
     catch (Exception e) {
       e.printStackTrace();
     }
   }
 
   public void closeSplashScreen() {
     sp.setCursor(Cursor.getDefaultCursor());
     sp.setVisible(false);
     f.setVisible(true) ;
     sp.dispose();
   }
 
}
