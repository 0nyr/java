import java.net.*;
import java.io.*;

public class CnxServer extends Thread {
   private ServerSocket serverSocket;
   private int cnxCount;
   private ClientManager manager;
   private boolean isAuthorized=true;
   
   public CnxServer(int port, int maxNbOfPlayers, int currentMap) throws IOException {
      serverSocket = new ServerSocket(port);
      cnxCount=0;
      manager=new ClientManager(this, maxNbOfPlayers, currentMap);
      manager.start();
//      serverSocket.setSoTimeout(10000);
   }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public void run() {
      while(true) {
         try {
             if (isAuthorized) {
                 System.out.println("Waiting for client on port " + InetAddress.getLocalHost() + " : " + serverSocket.getLocalPort() + "...");
                 Socket server = serverSocket.accept();

                 //nouvelle connexion d'un client
                 cnxCount++;

                 System.out.println("-> " + cnxCount + ") Just connected to " + server.getRemoteSocketAddress());
                 DataInputStream in = new DataInputStream(server.getInputStream());
                 System.out.println(in.readUTF());
                 DataOutputStream out = new DataOutputStream(server.getOutputStream());
                 out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
                         + "you are client " + cnxCount + " \n");

                 ObjectOutputStream toClient = new ObjectOutputStream(out);
                 ObjectInputStream fromClient = new ObjectInputStream(in);
                 System.out.println("\t getting transaction : ");
                 try {
                     Transaction t = (Transaction) fromClient.readObject();
                     System.out.println("\t\t Client says transaction : " + t);
                     t.setNum(cnxCount);
                     System.out.println("\t\t Sending back : " + t);
                     toClient.writeObject(t);
                     manager.addClient(new Client(t.getName(), t.isCreator(), cnxCount, t.getPort(), server.getInetAddress()));

                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                 cnxCount=manager.getNbOfClients(); //actualise le nombre de clients connectés afin que le serveur sache s'il a le droit d'accepter de nouveau joueurs
             }  //end of the if loop
//            server.close();

         } catch(SocketTimeoutException s){
             System.out.println("Socket timed out!");
             break;
         } catch(IOException e){
             e.printStackTrace();
             break;
         }
      }
   }
}
