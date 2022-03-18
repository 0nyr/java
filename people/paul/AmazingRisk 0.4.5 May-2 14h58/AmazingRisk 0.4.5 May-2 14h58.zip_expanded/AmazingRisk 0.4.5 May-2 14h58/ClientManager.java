import java.util.*;

public class ClientManager extends Thread {

	private LinkedList<Client> listeClients;
	private LinkedList<Client> newClients;
	private LinkedList<Client> purge;
	private Data data;
	private CnxServer cnxServer;
	private int maxNbOfPlayers;
	private int chosenMap;
	private boolean nbOfPlayersModified=false;
	private boolean gameLaunched=false;
	
	public ClientManager(CnxServer cnxServer, int maxNbOfPlayers, int chosenMap){
		listeClients = new LinkedList<>();
		newClients = new LinkedList<>();
		data=new Data();
		this.cnxServer=cnxServer;
		this.maxNbOfPlayers=maxNbOfPlayers;
		this.chosenMap=chosenMap;
	}
	
	/* Pour eviter qu'un add soit fait pendant une boucle sur listeClients
	 * addClient l'ajoute dans une liste temporaire ajoutée en début de cycle du thread
	 */
	 
	public void addClient(Client c){
		newClients.add(c);
		System.out.println("Nouveau client ajouté ! "+c);
	}

	public int getNbOfClients() {
		return listeClients.size();
	}
	
	public void run() {
		while(true) {

			//DEBUT PARTIE QUI TRAITE DU CHANGEMENT DE NOMBRE DE JOUEURS
			nbOfPlayersModified=false;

			//Si pb avec client, on purge et on indique que le nb de joueurs a changé : il va falloir actualiser
			purge = new LinkedList<>(); // liste de clients à éliminer si pb
			for (Client c : listeClients) {
				if(!c.isConnected()) {
					purge.add(c);
					nbOfPlayersModified=true;
				}
			}

			for(Client c:purge)
				listeClients.remove(c);

			//Si nouveau client, on l'ajoute et on indique qu'il va falloir actualiser
			if(newClients.size()>0){  // intégration des nouveaux clients dans listeClients
				nbOfPlayersModified=true;
				System.out.print("New clients arrived : ");
				for(Client c:newClients){
					if(c.isConnected()){
						System.out.print("("+c+")\t");
						listeClients.add(c);
						data.addElt(new PlayerServerMenuCreator(maxNbOfPlayers,chosenMap,c));
						c.sendData(data);	//on envoie les données nécessaires au nouveau client pour créer sa frame
						data.clear();
					}
				}
				System.out.println("\t list updated");
				newClients = new LinkedList<>();
			}



			if (nbOfPlayersModified) {
				for(Client c : listeClients) {
					data.addElt(new PlayerServerMenuEditor(listeClients));
					c.sendData(data);
					data.clear();
				}
			}
			//FIN PARTIE QUI TRAITE DU CHANGEMENT DE NB DE JOUEURS



			//Réception des données + traitement si nécessaire
			boolean update=false;
			for(Client c:listeClients){  // réception des données
				if(c.isConnected()&& c.updateData()){
					System.out.print("\t Updating data from client "+c+" : ");
					System.out.println(c.getData());
/*
					if(clientData instanceof PlayerServerMenuEditor) {	//la seule chose qui peut changer c'est la couleur lorsqu'un client recoit un "PlayerServerMenuEditor"
						((PlayerServerMenuEditor) clientData).setClientColor(c);
					}
*/
					update=true;
				}
			}

			if (update){ // si nouvelles données, diffusion à tous
				System.out.println("New data arrived : updating client list");
				this.majData();
				this.decodeData();
				/*
				this.majData();
				for(Client c:listeClients){
					System.out.print("\t Client "+c);
					c.sendData(data);
					if(!c.isConnected()){ // si pb avec un client, on l'enlève de la liste mais après la boucle
						System.out.println(" seems disconnected : removed from list");
						purge.add(c);
						if(!gameLaunched) {
							nbOfPlayersModified = true;	//si le jeu n'est pas lancé, des clients peuvent partir et être déconnectés => on ne gère pour le moment pas les déconnexions pdt le jeu
						}
					}
					else
						System.out.println(" updated ");
				}*/
			}


			if(listeClients.size()==maxNbOfPlayers) {
				cnxServer.setAuthorized(false);
			}

			//pour s'assurer qu'il n'y aura plus de data au retour dans la boucle
			data.clear();
			try{
				sleep(10);	//permet de ne pas faire de System.out.println à l'infini trop vite. on enlevera le sleep quand il n'y en aura plus
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	private void decodeData() {
		for(DataElt d : data.getList()) {
			if (d instanceof PlayerServerMenuEditor) {

			}
		}
	}


	public void majData(){
//		Data d=new Data();
		for(Client c:listeClients)
			data.addElt(c.getData());
//		data=d;
	}
	
	public String toString(){
		String s=" - "+listeClients.size()+" connected clients :\n";
		for(Client c:listeClients)
			s+="\t"+c+"\n";
		return s;
	}
	
}
