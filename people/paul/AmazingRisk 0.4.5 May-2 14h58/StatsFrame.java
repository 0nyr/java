import java.awt.*;
import javax.swing.*;
import java.util.* ;
import org.knowm.xchart.*;

public class StatsFrame extends JFrame {
	
	private JTabbedPane jtp ;
	private Sync sync ;
	private XChartPanel[] XChartsTab ;
	
	public StatsFrame(Sync sync){
		super(TitleOf.StatsFrame) ;
		this.sync=sync ;

		jtp=new JTabbedPane() ; XChartsTab=new XChartPanel[6] ;
		
		XChartsTab[0]=new XChartPanel<>(new XYChart(0,0)); XChartsTab[1]=new XChartPanel<>(new XYChart(0,0));
		XChartsTab[2]=new XChartPanel<>(new PieChart(0,0)); XChartsTab[3]=new XChartPanel<>(new PieChart(0,0));
		XChartsTab[4]=new XChartPanel<>(new CategoryChart(0,0)) ; XChartsTab[5]=new XChartPanel<>(new CategoryChart(0,0)) ;
		
		LinkedList<Entity> allEntitiesList = sync.rkDatas.allEntitiesList ;
		LinkedList<Integer> axisX = new LinkedList<>() ;
		
		
		for(Entity entity : allEntitiesList){
			((XYChart)XChartsTab[0].getChart()).addSeries(entity.getName(), entity.getArmiesDatas());
		}
		graphicalXYOperations(XChartsTab[0],"EVOLUTION OF THE ARMIES OF EACH PLAYER/AI","Turn","Armies") ;
		
		for(Entity entity : allEntitiesList){
			((XYChart)XChartsTab[1].getChart()).addSeries(entity.getName(), entity.getCountriesDatas());
		}
		graphicalXYOperations(XChartsTab[1],"EVOLUTION OF THE NUMBER OF COUNTRIES FOR EACH PLAYER/AI","Turn","Number of countries") ;
		
		for(Entity entity : sync.entitiesList){
			((PieChart)XChartsTab[2].getChart()).addSeries(entity.getName(), entity.getArmiesDatas().getLast());
		}
		graphicalPieOperations(XChartsTab[2],"DIVISION OF THE ARMIES PER PLAYER/AI") ;
		
		for(Entity entity : sync.entitiesList){
			((PieChart)XChartsTab[3].getChart()).addSeries(entity.getName(), entity.getCountriesDatas().getLast());
		}
		graphicalPieOperations(XChartsTab[3],"DIVISION OF THE COUNTRIES PER PLAYER/AI") ;

		for(Entity entity : sync.entitiesList){
			//Le choix a été fait de contruire un graphe non-conventionnelle mais lisible. Le code semble ne vouloir rien dire.
			//En réalité, c'est du bricolage pour utiliser un graphe pour un autre dessein qu'usuellement.
			//Là, on crée une série d'une valeur à chaque fois, car les couleurs des séries seront les couleurs des joueurs.
			//Et on veut que les joueurs soient reconnus par leur couleur, donc un joueur équivaut à une série dans ce graphe.
			//C'est pourquoi on est des listes d'une seule valeur... C'est pourquoi on met un axe X qui veut rien dire et qu'on
			//affichera pas.
			
			LinkedList<String> xAxis = new LinkedList<>() ; xAxis.add("Not-Displayed") ;
			((CategoryChart)XChartsTab[4].getChart()).addSeries(entity.getName(),xAxis, getEntityArmies(entity));
		}
		graphicalCCOperations(XChartsTab[4],"Number of armies per player/AI for the current turn","Players/AI by their own colour","Armies") ;
		
		for(Entity entity : sync.entitiesList){
			//IDEM que le paragraphe précédent.
			
			LinkedList<String> xAxis = new LinkedList<>() ; xAxis.add("Not-Displayed") ;
			((CategoryChart)XChartsTab[5].getChart()).addSeries(entity.getName(),xAxis, getEntityCountries(entity));
		}
		graphicalCCOperations(XChartsTab[5],"Number of countries per player/AI for the current turn","Players/AI by their own colour","Number of countries") ;
		
		
		jtp.addTab("Troops evolution",new ImageIcon("icons/graph.png"),XChartsTab[0],"Number of armies of each player per turn");
		jtp.addTab("Territory evolution",new ImageIcon("icons/graph.png"),XChartsTab[1],"Number of countries of each player per turn");
		jtp.addTab("Troops division",new ImageIcon("icons/camembert.png"),XChartsTab[2],"Division of the armies per player/AI");
		jtp.addTab("Map division",new ImageIcon("icons/camembert.png"),XChartsTab[3],"Division of the countries per player/AI");
		jtp.addTab("Troops distribution",new ImageIcon("icons/histogram.png"),XChartsTab[4],"Number of armies per player/AI for the current turn");
		jtp.addTab("Countries distribution",new ImageIcon("icons/histogram.png"),XChartsTab[5],"Number of countries per player/AI for the current turn");
		
		add(jtp) ;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setSize(new Dimension(900,500)) ;
		validate() ;
		repaint() ;
		setVisible(true);
		
	}
	
	private void graphicalXYOperations(XChartPanel XCPanel, String title, String xTitle, String yTitle) {
		XYChart chart=(XYChart)XCPanel.getChart() ;
		chart.setXAxisTitle(xTitle) ;
		chart.setYAxisTitle(yTitle) ;
		chart.setTitle(title) ;
		chart.getStyler().setPlotGridVerticalLinesVisible(false) ; 
		chart.getStyler().setPlotBackgroundColor(Color.BLACK) ;
		chart.getStyler().setChartBackgroundColor(Color.WHITE) ;
		chart.getStyler().setLegendPosition(org.knowm.xchart.style.Styler.LegendPosition.InsideNW);
		chart.getStyler().setSeriesColors(getSeriesColours(sync.rkDatas.allEntitiesList)) ;
	}
	
	private void graphicalPieOperations(XChartPanel XCPanel, String title) {
		PieChart chart=(PieChart)XCPanel.getChart() ;
		chart.setTitle(title) ;
		chart.getStyler().setPlotBackgroundColor(Color.BLACK) ;
		chart.getStyler().setChartBackgroundColor(Color.WHITE) ;
		chart.getStyler().setLegendPosition(org.knowm.xchart.style.Styler.LegendPosition.InsideNW);
		chart.getStyler().setSeriesColors(getSeriesColours(sync.entitiesList)) ;
	}
	
	private void graphicalCCOperations(XChartPanel XCPanel, String title, String xTitle, String yTitle) {
		CategoryChart chart=(CategoryChart)XCPanel.getChart() ;
		chart.setTitle(title) ;
		chart.getStyler().setPlotBackgroundColor(Color.BLACK) ;
		chart.getStyler().setPlotGridVerticalLinesVisible(false) ;
		chart.getStyler().setXAxisTicksVisible(false) ;
		chart.getStyler().setChartBackgroundColor(Color.WHITE) ;
		chart.getStyler().setSeriesColors(getSeriesColours(sync.entitiesList)) ;
		chart.setXAxisTitle(xTitle) ;
		chart.setYAxisTitle(yTitle) ;
	}
	
	private Color[] getSeriesColours(LinkedList<Entity> entities){
		Color[] toReturn = new Color[entities.size()] ;
		
		for(int e=0 ; e<toReturn.length ; e++){
			toReturn[e]=getColourFromString(entities.get(e).getAttributedColour()) ;
		}
		return toReturn ;
	}
			
	private Color getColourFromString(String stringColour){
		Color toReturn = Color.WHITE ;
		if(stringColour.equalsIgnoreCase("gray")) {
			toReturn = Color.GRAY ;
		} else if(stringColour.equalsIgnoreCase("green")){
			toReturn = Color.GREEN ;
		} else if(stringColour.equalsIgnoreCase("blue")){
			toReturn = Color.BLUE ;
		} else if(stringColour.equalsIgnoreCase("red")){
			toReturn = Color.RED ;
		} else if(stringColour.equalsIgnoreCase("orange")){
			toReturn = Color.ORANGE ;
		} else if(stringColour.equalsIgnoreCase("yellow")){
			toReturn = Color.YELLOW ;
		} else if(stringColour.equalsIgnoreCase("pink")){
			toReturn = Color.PINK ;
		} else if(stringColour.equalsIgnoreCase("white")){
			toReturn = Color.WHITE ;
		} else if(stringColour.equalsIgnoreCase("magenta")){
			toReturn = Color.MAGENTA ;
		}else if(stringColour.equalsIgnoreCase("cyan")){
			toReturn = Color.CYAN ;
		}
		
		return toReturn ;
	}
	
	private LinkedList<String> getEntitiesNames(LinkedList<Entity> entitiesList){
		LinkedList<String> toReturn = new LinkedList<>() ;
		for(Entity entity : entitiesList){
			toReturn.add(entity.getName()) ;
		}
		
		return toReturn ;
	}
	private LinkedList<Integer> getEntityArmies(Entity entity){
		
		LinkedList<Integer> toReturn = new LinkedList<>() ;
		toReturn.add(entity.getArmiesDatas().getLast()) ;
		
		return toReturn ;
	}

	private LinkedList<Integer> getEntityCountries(Entity entity){
		
		LinkedList<Integer> toReturn = new LinkedList<>() ;
		toReturn.add(entity.getCountriesDatas().getLast()) ;
		
		return toReturn ;
	}
	/*private int getMaximumNbOfTurn(LinkedList<Entity> entitiesList){
		
		int maxNbOfTurn=0 ;
		
		for(Entity entity : entitiesList) {
			if(entity.getArmiesDatas().size()>maxNbOfTurn){
				maxNbOfTurn=entity.getArmiesDatas().size() ;
			}
		}
		return maxNbOfTurn ;
	}*/

}
