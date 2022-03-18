/*
 * Transaction.java
 * 
 * Copyright 2019 hrivano <hrivano@DESKTOP-DH54B4A>
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


public class Transaction implements java.io.Serializable {
	private int clientPort;
	private int clientNum;
	private String clientName;
	private boolean isCreator;
	
	public Transaction(String clientName,boolean isCreator, int port){
		clientPort = port;
		clientNum = -1;
		this.clientName = clientName;
		this.isCreator=isCreator;
	}
	
	public void setNum(int num){
		clientNum = num;
	}
	
	public String getName(){
		return clientName;
	}

	public boolean isCreator() {
		return isCreator;
	}

	public int getPort(){
		return clientPort;
	}
	
	public String toString(){
		return clientName+" ("+clientNum+") on port "+clientPort;
	}
	
}

