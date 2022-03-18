import java.net.*;
import java.io.*;


public class ClientCnx extends Thread{

	int port;
 	ObjectOutputStream toServ;
	ObjectInputStream fromServ;
	ServerSocket serverSocket;
	
	Data data;

	private GreetingClient gt;

   public ClientCnx(GreetingClient gt) throws IOException {
      serverSocket = new ServerSocket(0);
      port = serverSocket.getLocalPort();
      data=new Data();
      this.gt=gt;
      
//      serverSocket.setSoTimeout(10000);
   }

	public void run() {
		try {
			System.out.println("Waiting for server on port " + InetAddress.getLocalHost() +" : " +serverSocket.getLocalPort() + "...");
            Socket client = serverSocket.accept();
            
            System.out.println("\t Server cnx arrived from " + client.getRemoteSocketAddress());
            fromServ = new ObjectInputStream(client.getInputStream()); 
            toServ = new ObjectOutputStream(client.getOutputStream());
            
            System.out.println((String)fromServ.readObject());
               
			System.out.println("\t waiting for input transaction : ");
			while(true) {
				if(this.receiveData()){
					System.out.println("\t\t Server says : "+data);
					gt.hasToDecode(data);
					//put code to handle modified data if needed
				}
				else
					System.out.println("Server silent");
				sleep(1000);
			}
//			client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
   }

	private boolean receiveData(){
		boolean update=false;
		try{
//			if(fromServ.available()>0){
				Object o = fromServ.readObject();
				if (o instanceof Data){
					Data d=(Data)o;
//					if(!d.equals(data)){
						data=d;
						update=true;
//					}
//				}
			}	
		} catch(Exception e){  //Oups
			e.printStackTrace();
		}
		return update;
	}

	public void sendDataElt(DataElt elt){
		try{
			System.out.println("Sending "+elt+" to Server");
			toServ.writeObject(elt);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public Data getData(){
		return data;
	}

	public int getPort(){
		return this.port;
	}

}

