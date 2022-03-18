
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JButton;
 
public class AmazingButton extends JButton {
 
	private int inset = 5;
	private boolean mousePressed=false ;
	private boolean mouseOver ;
 
 
	public AmazingButton(String aName){
		super(aName);
		setContentAreaFilled(false);
		setForeground(Color.white);
		setFocusPainted(false) ; //I don't want to have a kind of rectangle which displays itself when I click on the button!
		
		setFont(new Font(Font.SANS_SERIF,Font.BOLD,14)) ; // It is possible to modify the Font if we want and have a control on the font.
		
		//The overrides are used to repaint the button when I click or exit the button.
		MouseAdapter mouseListener = new MouseAdapter(){
			
			public void mousePressed(MouseEvent me){
				if(contains(me.getX(), me.getY())){
					mousePressed = true;
					repaint();
				}
			}
	
			public void mouseReleased(MouseEvent me){
				mousePressed = false;
				repaint();
			}
			
			public void mouseExited(MouseEvent me){
				mouseOver = false;
				mousePressed = false;
				repaint();
			}
			
			public void mouseMoved(MouseEvent me){
				mouseOver = contains(me.getX(), me.getY());
				repaint();
			}
		
		};
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
	}
 
 
	protected void paintComponent(Graphics g){
		setForeground(Color.white) ;
		Color buttonColor= Color.blue ;
		if(mousePressed){
			buttonColor= Color.darkGray ;
		}
		if(mouseOver){
			setForeground(Color.lightGray) ;
		}
			
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
		int vWidth = getWidth();
		int vHeight = getHeight();
 
		// Calculate the size of the button
		int vButtonHeight = vHeight - (inset * 2);
		int vButtonWidth = vWidth - (inset * 2);
		int vArcSize = vButtonHeight;
 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
		// Create the gradient paint for the first layer of the button
		Color vGradientStartColor =  buttonColor.darker().darker().darker();
		Color vGradientEndColor = buttonColor.brighter().brighter().brighter();
		Paint vPaint = new GradientPaint(0, inset, vGradientStartColor, 0, vButtonHeight, vGradientEndColor, false);
		g2d.setPaint(vPaint);
 
		// Paint the first layer of the button
		g2d.fillRoundRect(inset, inset, vButtonWidth, vButtonHeight, vArcSize, vArcSize);
 
		// Calulate the size of the second layer of the button
		int vHighlightInset = 2;
		int vButtonHighlightHeight = vButtonHeight - (vHighlightInset*2);
		int vButtonHighlightWidth = vButtonWidth - (vHighlightInset * 2);
		int vHighlightArcSize = vButtonHighlightHeight;
 
		// Create the paint for the second layer of the button
		vGradientStartColor = Color.WHITE;
		vGradientEndColor = buttonColor.brighter();
		vPaint = new GradientPaint(0,inset+vHighlightInset,vGradientStartColor,0,inset+vHighlightInset+(vButtonHighlightHeight/2), buttonColor.brighter(), false);
 
		// Paint the second layer of the button
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,.8f));
		g2d.setPaint(vPaint);
 
		g2d.fillRoundRect(inset+vHighlightInset,inset+vHighlightInset,vButtonHighlightWidth,vButtonHighlightHeight,vHighlightArcSize,vHighlightArcSize);
 
		RoundRectangle2D.Float r2d =new RoundRectangle2D.Float(inset, inset, vButtonWidth, vButtonHeight, vArcSize, vArcSize);
		g2d.clip(r2d);		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
		super.paintComponent(g);
 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
}
