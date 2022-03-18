import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Image;
import java.awt.*;
import javax.swing.*;

public class IconButton extends JButton{
	
	private ImageIcon icon ;
	private int width, height ;
	private boolean mouseOver=false ;
	private boolean mousePressed=false ;


	public IconButton(String txt, ImageIcon img, int width, int height){
		super(txt, img);
		icon=img;
		this.width=width ;
		this.height=height ;
		
		MouseAdapter mouseListener = new MouseAdapter(){
			
			public void mouseEntered(MouseEvent me){
					mouseOver=true ;
			}
			
			public void mouseExited(MouseEvent me){
				mouseOver=false ;
				mousePressed=false;
			}
			public void mousePressed(MouseEvent me){
				mousePressed=true ;
				mouseOver=false ;
			}
			public void mouseReleased(MouseEvent me){
				mousePressed=false ;
				mouseOver=contains(me.getX(), me.getY());
			}
		
		};
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
	}
		

	@Override
	public void paintComponent(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
		BasicStroke line = new BasicStroke(8.0f);
		g2.setStroke(line);
		
		
		g.drawImage(icon.getImage(), 0, 0, width, height, this) ; //THIS IS THE INTERESTING POINT FOR REDEFINE : have the control on the size of the displayed map !
		if(mouseOver || mousePressed){
			if(mouseOver){
				g2.setColor(Color.BLACK) ;
				
			} else if(mousePressed){
				g2.setColor(Color.BLUE) ;
			}	
			g2.drawRect(0, 0,width, height) ;
		}
	}

}
