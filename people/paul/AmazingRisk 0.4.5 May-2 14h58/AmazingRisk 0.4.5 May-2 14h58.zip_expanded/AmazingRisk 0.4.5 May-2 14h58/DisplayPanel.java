import java.awt.*;
import javax.swing.*;
import java.util.LinkedList; //ADDJEREM
 
public class DisplayPanel extends JPanel {

    private int w, h;
    private Image map ;
    private ImageIcon mapIcon ;
    private boolean drawLine=false ;
    private LinkedList<Integer> x1,y1,x2,y2 ;
    private Color colour ;
     
    public DisplayPanel(ImageIcon map, int w, int h){
        this.map = map.getImage() ;
        this.mapIcon = map ;
        this.w=w ;
        this.h=h ;
        this.setLayout(null);
        x1=new LinkedList<>();
        y1=new LinkedList<>();
        x2=new LinkedList<>();
        y2=new LinkedList<>();
    }
    
    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        if(map!=null) {
            g1.drawImage(map, 0, 0  , w, h,this);
        }

        if(drawLine){
			Graphics2D g2 = (Graphics2D) g1;
			BasicStroke line = new BasicStroke(8.0f);
			g2.setStroke(line);
			g2.setColor(colour);
			for(int k=0 ; k<x1.size() ; k++){
				g2.drawLine(x1.get(k),y1.get(k),x2.get(k),y2.get(k));
			}
		}
	}
    public void setLine(int x1, int y1, int x2, int y2, Color colour){
		this.x1.add(x1); this.y1.add(y1) ; this.x2.add(x2) ; this.y2.add(y2) ;this.colour=colour ;
		drawLine=true ;
		repaint();
	}
	public LinkedList<Integer> getX1List(){
		return x1 ;
	}
	public LinkedList<Integer> getY1List(){
		return y1 ;
	}
	public LinkedList<Integer> getX2List(){
		return x2 ;
	}
	public LinkedList<Integer> getY2List(){
		return y2 ;
	}
	
	public void removeLine(){
		drawLine=false ;
		x1.clear() ; x2.clear() ; y1.clear() ; y2.clear() ;
		repaint();
	}

    public void setSize(int w, int h) {
        this.w=w;
        this.h=h;
    }
    public ImageIcon getIcon() {
		return mapIcon ;
	}
}
