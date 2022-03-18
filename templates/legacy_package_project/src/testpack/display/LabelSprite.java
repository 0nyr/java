package testpack.display;

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
			//NB: The compiled code has acces to /build, hence the img is in ../res/
            //img = ImageIO.read(new File("../res/pixel_art_onyr_120x51.png"));
            //img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("./res/pixel_art_onyr_120x51.png"));
            img = ImageIO.read(getClass().getResourceAsStream("/res/pixel_art_onyr_120x51.png"));
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
	
}
