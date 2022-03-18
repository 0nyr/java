import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CountryButton extends JButton{
	
	private boolean mouseOver = false;
	private boolean mousePressed = false;
	private Country country ;
	private int a=0 ;
	private Color colour ;
	private Timer timer;
	private boolean circleAround=false ; //ADDJEREM
	
	//private JPopupMenu countryMenu ;

	public CountryButton(Country country){
		super(Integer.toString(country.getArmies())); //The displayed nb of armies is given by the associated country
		setOpaque(false);
		setFocusPainted(false);
		setBorderPainted(false); //Set the square borders transparent (not painted in order to display just a round, not a square)
		
		this.country=country ;
		
		MouseAdapter mouseListener = new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)){
					Entity currentEntity=country.sync.getCurrentEntity();
                    if(country.getOwner().equals(currentEntity) && currentEntity.getToe().length()!=3) {	//permet de n'autoriser l'ouverture de popup de renommage du pays qu'au joueur possédant ce pays, et empeche de renommer les IA
						(new PopupMenu(country)).show( e.getComponent(), e.getX(), e.getY() );
					}
                }
                else{
                    country.isClicked();
					repaint();
                }
			}
		};

		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);		
	}
	
	private int getDiameter(){
		int diameter = Math.min(getWidth(), getHeight());
		return diameter;
	}
	
	public int getRadius(){
		int radius= (int)(getDiameter()/2.0) ;
		return radius ;
	}

	@Override
	public Dimension getPreferredSize(){
		FontMetrics metrics = getGraphics().getFontMetrics(getFont());
		int minDiameter = 10 + Math.max(metrics.stringWidth(getText()), metrics.getHeight());
		return new Dimension(minDiameter, minDiameter);
	}
	
	@Override
	public boolean contains(int x, int y){
		int radius = getDiameter()/2;
		return Point2D.distance(x, y, getWidth()/2, getHeight()/2) < radius;
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g1 = (Graphics2D) g;
		BasicStroke line = new BasicStroke(8.0f);
		g1.setStroke(line); //In order to have the same aspect during all the game (c.f. displaypanal Graphics 2D)
		
		setText(Integer.toString(country.getArmies()));
		if(country.getArmies()>0){
			setToolTipText("<html>" + country.getCountryName() + " (" + country.getContinent() + ") " + "<br>Owner : "+country.getOwner().toString()+"<br>Armies : "+country.getArmies()+" </html>") ;
		} else{
			setToolTipText("<html>" + country.getCountryName() + " (" + country.getContinent() + ") " + "<br>Owner : None (free country)<br>Armies : None </html>") ;
		}

		int diameter = getDiameter();
		int radius = diameter/2;

		if(mousePressed){
			g.setColor(Color.BLACK);
		}else {
			if(country.getOwner()!=null) {
				if(country.getOwner().getAttributedColour().equalsIgnoreCase("gray")) {
					g.setColor(Color.GRAY);
				} else if(country.getOwner().getAttributedColour().equalsIgnoreCase("green")){
					g.setColor(Color.GREEN);
				} else if(country.getOwner().getAttributedColour().equalsIgnoreCase("blue")){
					g.setColor(Color.BLUE);
				} else if(country.getOwner().getAttributedColour().equalsIgnoreCase("red")){
					g.setColor(Color.RED);
				} else if(country.getOwner().getAttributedColour().equalsIgnoreCase("brown")){
					g.setColor(Color.ORANGE);
				} else if(country.getOwner().getAttributedColour().equalsIgnoreCase("yellow")){
					g.setColor(Color.YELLOW);
				} else if(country.getOwner().getAttributedColour().equalsIgnoreCase("pink")){
					g.setColor(Color.PINK);
				} else if(country.getOwner().getAttributedColour().equalsIgnoreCase("white")){
					g.setColor(Color.WHITE);
				} else if(country.getOwner().getAttributedColour().equalsIgnoreCase("magenta")){
					g.setColor(Color.MAGENTA);
				}else if(country.getOwner().getAttributedColour().equalsIgnoreCase("cyan")){
					g.setColor(Color.CYAN);
				}
			} else {
				g.setColor(Color.BLACK);
			}
		}

		colour=g.getColor() ;
		g.fillOval(getWidth()/2 - radius, getHeight()/2 - radius, diameter, diameter);
		
		if(mouseOver){
			g.setColor(Color.DARK_GRAY);
		}
		else{
			g.setColor(Color.BLACK);
		}
		
		if(circleAround){ //ADDJEREM
			g.drawOval(getWidth()/2 - radius, getHeight()/2 - radius, diameter, diameter);
		}
		g.setColor(Color.BLACK);
		g.setFont(getFont());
		FontMetrics metrics = g.getFontMetrics(getFont());
		int stringWidth = metrics.stringWidth(getText());
		int stringHeight = metrics.getHeight();
		g.drawString(getText(), getWidth()/2 - stringWidth/2, getHeight()/2 + stringHeight/4);
	}
	public Color getColour(){
		return colour ;
	}

	public Country getCountry() {
		return country ;
	}
	
	public void setCircleAround(boolean b){
		circleAround=b ;
	}
}
