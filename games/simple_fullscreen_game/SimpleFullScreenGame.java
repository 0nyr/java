
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;

public class SimpleFullScreenGame {
        
  static boolean running;
        
  public static void main( String[] args ) {
                
    // Create game window...
    JFrame app = new JFrame();
    app.setIgnoreRepaint( true );
    app.setUndecorated( true );
                
    // Add ESC listener to quit...
    app.addKeyListener( new KeyAdapter() {
      public void keyPressed( KeyEvent e ) {
        if( e.getKeyCode() == KeyEvent.VK_ESCAPE )
            running = false;
          }
    });
                
    // Get graphics configuration...
    GraphicsEnvironment ge = 
        GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gd = ge.getDefaultScreenDevice();
    GraphicsConfiguration gc = gd.getDefaultConfiguration();

    // Change to full screen
    gd.setFullScreenWindow( app );
    if( gd.isDisplayChangeSupported() ) {
      gd.setDisplayMode( 
        new DisplayMode( 640, 480, 32, DisplayMode.REFRESH_RATE_UNKNOWN )
      );
    }
                
    // Create BackBuffer...
    app.createBufferStrategy( 2 );
    BufferStrategy buffer = app.getBufferStrategy();
                
    // Create off-screen drawing surface
    BufferedImage bi = gc.createCompatibleImage( 640, 480 );

    // Objects needed for rendering...
    Graphics graphics = null;
    Graphics2D g2d = null;
    Color background = Color.BLACK;
    Random rand = new Random();
                
    // Variables for counting frames per seconds
    int fps = 0;
    int frames = 0;
    long totalTime = 0;
    long curTime = System.currentTimeMillis();
    long lastTime = curTime;
                
    running = true;
    while( running ) {
      try {
        // count Frames per second...
        lastTime = curTime;
        curTime = System.currentTimeMillis();
        totalTime += curTime - lastTime;
        if( totalTime > 1000 ) {
          totalTime -= 1000;
          fps = frames;
          frames = 0;
        } 
        ++frames;

        // clear back buffer...
        g2d = bi.createGraphics();
        g2d.setColor( background );
        g2d.fillRect( 0, 0, 639, 479 );
                                
        // draw some rectangles...
        for( int i = 0; i < 20; ++i ) {
          int r = rand.nextInt(256);
          int g = rand.nextInt(256);
          int b = rand.nextInt(256);
          g2d.setColor( new Color(r,g,b) );
          int x = rand.nextInt( 640/2 );
          int y = rand.nextInt( 480/2 );
          int w = rand.nextInt( 640/2 );
          int h = rand.nextInt( 480/2 );
          g2d.fillRect( x, y, w, h );
        }
                                
        // display frames per second...
        g2d.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );
        g2d.setColor( Color.GREEN );
        g2d.drawString( String.format( "FPS: %s", fps ), 20, 20 );
                                
        // Blit image and flip...
        graphics = buffer.getDrawGraphics();
        graphics.drawImage( bi, 0, 0, null );
                                
        if( !buffer.contentsLost() )
          buffer.show();
                                
      } finally {
        // release resources
        if( graphics != null ) 
          graphics.dispose();
        if( g2d != null ) 
          g2d.dispose();
      }
    }
                
    gd.setFullScreenWindow( null );
    System.exit(0);
  }
}
