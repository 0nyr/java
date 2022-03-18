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
 
import javax.swing.JLabel;
 
public class StepLabel extends JLabel {
 
	private int inset = 5;
	private boolean colourChanged = false ;
	private Color buttonColor ;
	private Sync sync ;
	/*private boolean mousePressed=false ;
	private boolean mouseOver ;*/
 
 
	public StepLabel(String txt, Sync sync){
		super(txt); //In order to don't have the text too close to the border !
		this.sync=sync ;
		setForeground(Color.white);
		
		setFont(new Font(Font.SANS_SERIF,Font.PLAIN, 14)) ; // It is possible to modify the Font if we want and have a control on the font size.
	}
	
	public void setText(String t){
		super.setText("     "+t) ;
	}
 
 
	protected void paintComponent(Graphics g){
		String currentColor = sync.getCurrentEntity().getAttributedColour();
		setForeground(Color.WHITE) ;
		if(currentColor.equalsIgnoreCase("gray")) {
			buttonColor= Color.GRAY ;
			setForeground(Color.BLACK) ;
		} else if(currentColor.equalsIgnoreCase("green")) {
			buttonColor= Color.GREEN ;
			setForeground(Color.BLACK) ;
		} else if(currentColor.equalsIgnoreCase("blue")) {
			buttonColor= Color.BLUE ;
		} else if(currentColor.equalsIgnoreCase("red")) {
			buttonColor= Color.RED ;
		} else if(currentColor.equalsIgnoreCase("brown")) {
			buttonColor= Color.ORANGE ;
		} else if(currentColor.equalsIgnoreCase("yellow")) {
			buttonColor= Color.YELLOW ;
			setForeground(Color.BLACK) ;
		} else if(currentColor.equalsIgnoreCase("pink")) {
			buttonColor= Color.PINK ;
			setForeground(Color.BLACK) ;
		} else if(currentColor.equalsIgnoreCase("white")) {
			buttonColor= Color.WHITE ;
			setForeground(Color.BLACK) ;
		} else if(currentColor.equalsIgnoreCase("magenta")) {
			buttonColor= Color.MAGENTA ;
		} else if(currentColor.equalsIgnoreCase("cyan")) {
			buttonColor= Color.CYAN ;
			setForeground(Color.BLACK) ;
		} else {
			buttonColor= Color.BLACK ;
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
		vGradientEndColor = buttonColor;
		vPaint = new GradientPaint(0,inset+vHighlightInset,vGradientStartColor,0,inset+vHighlightInset+(vButtonHighlightHeight/2), buttonColor, false);
 
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
