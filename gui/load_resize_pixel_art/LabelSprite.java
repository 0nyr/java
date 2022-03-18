// @author Onyr
/*
 * This file is an example on how to load a single pixel art sprite image, how to resize it and
 * how to display it using JLabel as a base example component
 * 
 * NB: The key of this example is the following use of function:
 * Image imgResized = img.getScaledInstance(
			SPRITE_WIDTH * multiplySize,
			SPRITE_HEIGHT * multiplySize,
			Image.SCALE_SMOOTH);
 *
 * NB: Here, the sprite used for the example is a .png 120x51 pixels in size
*/

import java.awt.event.*;
import javax.swing.* ;
import java.awt.* ;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Image;

public class LabelSprite extends JLabel {
	
	// choses sprite characteristics
	int SPRITE_WIDTH = 120;
	int SPRITE_HEIGHT = 51;

    public LabelSprite(int x, int y, int multiplySize) {
        //this.setBounds(x, y, width, height);
        this.setLocation(x, y);
		this.setSpriteResized(multiplySize);
		this.setLabelSize(multiplySize);
        
    }
    
    /**
     * Set the Icon of the Label to the resized sprite
    */
    protected void setSpriteResized(int multiplySize) {
		// set the right sprite
        BufferedImage img = null;
        try {
             img = ImageIO.read(new File("./pixel_art_onyr_120x51.png"));
             System.out.println("load done");
        } catch (IOException loadError) {
            loadError.printStackTrace();
            System.out.println("load problem");
        }
        // resize the picture as a Buffered Image
        Image imgResized = img.getScaledInstance(
			SPRITE_WIDTH * multiplySize,
			SPRITE_HEIGHT * multiplySize,
			Image.SCALE_SMOOTH);
        // create an Image Icon
        ImageIcon imageIcon = new ImageIcon(imgResized);
        // set the image of the Label
        this.setIcon(imageIcon);
	}
	
	/** 
	 * Set the size of the label to the size of the resized sprite
	*/
	protected void setLabelSize(int multiplySize) {
		this.setSize(
			SPRITE_WIDTH * multiplySize,
			SPRITE_HEIGHT * multiplySize);
		
	}
    
    public static void main (String[] args) {
		
		// create a test JFrame
		JFrame frame = new JFrame("Demo load pixel art and resize it");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		// add instances of LabelSprite, multiplying each pixel by a factor 1, 2 and 4
		LabelSprite originalSprite = new LabelSprite(100, 10, 1);
		LabelSprite doubledSprite = new LabelSprite(100, 100, 2);
		LabelSprite pixelTimes4Sprite = new LabelSprite(100, 400, 4);
	
		frame.add(originalSprite);
		frame.add(doubledSprite);
		frame.add(pixelTimes4Sprite);
		frame.repaint();
		
	}
	
}
