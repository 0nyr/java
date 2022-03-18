/*
 * Client.java
 * 
 * Copyright 2019 Herve Rivano <hrivano@insa-lyon.fr>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */

import java.net.*;
import java.io.*;


public class Client {
	
	protected String playerName;
	protected boolean isCreator;
	protected String color;
	private int id;
	private int port;
	private InetAddress ip;
	private boolean connected;
	private ObjectOutputStream toClient;
	private ObjectInputStream fromClient;
	
	private DataElt data;
	private long lastUpdate;
	
	//cree un client et la socket de connexion
	public Client(String nom, boolean isCreator, int idNum, int portNum, InetAddress adresse) {
		this.playerName =nom;
		this.isCreator=isCreator;
		this.id=idNum;
		this.port=portNum;
		this.ip=adresse;
		
		this.lastUpdate= System.currentTimeMillis();
		
		try{
		 System.out.println("Connecting to " + ip + " on port " + port);
         Socket client = new Socket(ip, port);
         System.out.println("\t Just connected to " + client.getRemoteSocketAddress());

         toClient = new ObjectOutputStream(client.getOutputStream());
         fromClient = new ObjectInputStream(client.getInputStream());
         
         toClient.writeObject("Connexion ok");
         this.connected = true;
		} catch(Exception e){  //Oups on purge
			this.connected = false;
		}
	}
	
	
	//envoi d'un objet Data au client. Si pb, connected devient false et le client sera purgé
	public void sendData(Object o){
		try{
			System.out.println("\t \t Sending "+o+" to "+ playerName);
			toClient.writeObject(o);
		} catch(Exception e){  //Oups
			connected = false;
		}
	}
	
	//réception d'un DataElt, null sinon. Si pb, connected devient false et le client sera purgé
	private DataElt receiveData(){
		try{
			if(fromClient.available()>0){
				Object o = fromClient.readObject();
				if (o instanceof DataElt)
					return (DataElt)o;
			}
		} catch(Exception e){  //Oups
//			e.printStackTrace();
			connected = false;
		}
		return null;
	}
	
	//MàJ de data sans blocage et renvoie true si nouvelle donnée (en supposant que c'est ce qui est envoyé)
	// changer le type de data et getData pour changer de type d'objet
	public boolean updateData(){
		boolean update = false;
		DataElt d = this.receiveData();
		if((d != null)&&(!d.equals(data))){
			data = d;
			lastUpdate = System.currentTimeMillis();
			update=true;
		}
		return update;
	}
	
	public DataElt getData(){
		return data;
	}

	public long getLastUpdate(){
		return lastUpdate;
	}
	
	public boolean isConnected(){
		return connected;
	}
	
	public String toString(){
		return playerName +" ("+(System.currentTimeMillis()-lastUpdate)+") ";
	}
	
	
}

