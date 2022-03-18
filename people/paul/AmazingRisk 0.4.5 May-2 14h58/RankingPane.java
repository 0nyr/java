import java.awt.*;
import javax.swing.*;
import java.util.* ;

public class RankingPane extends JTabbedPane {
	
	private Sync sync ;
	private JPanel byCountriesPanel, byArmiesPanel ;
	private LinkedList<Entity> sortedEntitiesbyArmiesList, sortedEntitiesbyCountriesList ;
	
	
	public RankingPane(Sync sync){
		this.sync=sync ;
		byCountriesPanel=new JPanel(new FlowLayout()) ; byArmiesPanel=new JPanel(new FlowLayout()) ;
		LinkedList<Entity> entitiesList = sync.entitiesList ;
		
		LinkedList<Integer> armiesScoreList = new LinkedList<>() ;
		LinkedList<Integer> countriesScoreList = new LinkedList<>() ;
		
		for(Entity entity : entitiesList) {
			armiesScoreList.add(entity.getArmiesDatas().getLast()) ;
			countriesScoreList.add(entity.getCountriesDatas().getLast()) ;
		}
		
		Collections.sort(armiesScoreList, Collections.reverseOrder());
		Collections.sort(countriesScoreList, Collections.reverseOrder());
		
		sortedEntitiesbyArmiesList = sortTheEntitiesByArmies(entitiesList, armiesScoreList) ;
		sortedEntitiesbyCountriesList = sortTheEntitiesByCountries(entitiesList, countriesScoreList) ;
		
		JPanel gridPanel = new JPanel(new GridLayout(entitiesList.size(),1)) ;
		for(Entity entity : sortedEntitiesbyArmiesList){
			JLabel jl = new JLabel("<html><b>"+entity.getName()+"<br>Armies : "+entity.getArmiesDatas().getLast()+"<br>Countries : "+entity.getCountriesDatas().getLast()+"<br>________________________________________</b></html>") ;
			jl.setIcon(flagOf(entity,true)) ;
			gridPanel.add(jl) ;
		}
		JPanel gridPanel2 = new JPanel(new GridLayout(entitiesList.size(),1)) ;
		for(Entity entity : sortedEntitiesbyCountriesList){
			JLabel jl = new JLabel("<html><b>"+entity.getName()+"<br>Armies : "+entity.getArmiesDatas().getLast()+"<br>Countries : "+entity.getCountriesDatas().getLast()+"<br>________________________________________</b></html>") ;
			jl.setIcon(flagOf(entity,false)) ;
			gridPanel2.add(jl) ;
		}
		byArmiesPanel.add(gridPanel) ;
		byCountriesPanel.add(gridPanel2) ;

		setMaximumSize(new Dimension(700,300)) ;
		
		addTab("Il faut faire après l'ajout des scrollpane",byArmiesPanel);
		addTab("idem",byCountriesPanel);
		add(new JScrollPane(byArmiesPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),0) ;
		add(new JScrollPane(byCountriesPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),1) ;
		setTitleAt(0, "Ranking (by armies)") ; setIconAt(0,new ImageIcon("icons/graph.png")) ;
		setTitleAt(1,"Ranking (by countries)") ; setIconAt(1,new ImageIcon("icons/graph.png")) ;
	}
	
	
	private Icon flagOf(Entity entity, boolean byArmies) {
		ImageIcon flag = new ImageIcon("icons/"+entity.getAttributedColour()+"flag.png") ;
		
		if((byArmies && entity.equals(sortedEntitiesbyArmiesList.getFirst())) || (!byArmies && entity.equals(sortedEntitiesbyCountriesList.getFirst()))){
			flag=new ImageIcon("icons/"+entity.getAttributedColour()+"flagfirst.png") ;
		}
		return flag ;
	}
	
	private LinkedList<Entity> sortTheEntitiesByArmies(LinkedList<Entity> entitiesList, LinkedList<Integer> scoresList){ //Méthode de tri d'entité selon les scores des armées.
		LinkedList<Entity> toReturn = new LinkedList<>() ;
		
		for(int score : scoresList){
			boolean oneScoreforOneEntity=true ; //Eviter les doublons car plusieurs scores peuvent être les mêmes.
			for(Entity entity : entitiesList){
				boolean entityFound=false ;
				
				if(!entityFound && score==entity.getArmiesDatas().getLast()){
					boolean authorized=true ;
					for(Entity addedEntity : toReturn) {
						if(addedEntity.equals(entity)){
							authorized=false ;
						}
					}
					if(authorized){
						toReturn.add(entity) ;
						entityFound=true ;
					}
				}
				if(entityFound){
					break;
				}
			}
		}
		
		return toReturn ;
	}
	
	private LinkedList<Entity> sortTheEntitiesByCountries(LinkedList<Entity> entitiesList, LinkedList<Integer> scoresList){ //Méthode de tri d'entité selon les scores des armées.
		LinkedList<Entity> toReturn = new LinkedList<>() ;
		
		for(int score : scoresList){
			for(Entity entity : entitiesList){
				boolean entityFound=false ;
				if(!entityFound && score==entity.getCountriesDatas().getLast()){
					boolean authorized=true ;
					for(Entity addedEntity : toReturn) {
						if(addedEntity.equals(entity)){
							authorized=false ;
						}
					}
					if(authorized){
						toReturn.add(entity) ;
						entityFound=true ;
					}
				}
				if(entityFound){
					break;
				}

			}
		}
		
		return toReturn ;
	}


}
