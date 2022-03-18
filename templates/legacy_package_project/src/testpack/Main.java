/*
 * More info on how to use packages: https://docs.oracle.com/javase/tutorial/java/package/index.html
 */

package testpack;

import testpack.display.*;
import javax.swing.*;

public class Main {
	
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
