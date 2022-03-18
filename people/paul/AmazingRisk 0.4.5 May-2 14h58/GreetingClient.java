// File Name GreetingClient.java
import java.net.*;
import java.io.*;

public class GreetingClient {
    private ClientCnx cnx;
    private PlayerServerMenu psm;

    protected String playerName;
    protected boolean isCreator;
    protected String color;

    public GreetingClient(InetAddress serverIP, int portServer, String playerName, boolean isCreator) {
        try {
            this.playerName =playerName;
            this.isCreator=isCreator;
            System.out.println("Connecting to " + serverIP + " on port " + portServer);
            Socket client = new Socket(serverIP, portServer);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());

            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("Hello from " + client.getLocalSocketAddress());

            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("Server says " + in.readUTF());

            cnx = new ClientCnx(this);
            cnx.start();

            Transaction t = new Transaction(playerName, isCreator, cnx.getPort());

            ObjectOutputStream toServ = new ObjectOutputStream(outToServer);
            System.out.println("\t preparing transaction : " + t);
            toServ.writeObject(t);
            ObjectInputStream fromServ = new ObjectInputStream(inFromServer);

            try {
                t = (Transaction) fromServ.readObject();
                System.out.println("\t\t Server says transaction : " + t);
            } catch (Exception e) {
                e.printStackTrace();
            }
            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //methode de décodage des infos recues par le serveur afin de lancer les méthodes côté machine
    public void hasToDecode(Data dt) {
        /*<String> data = new LinkedList<>();
        StringTokenizer st = new StringTokenizer(dt.toString());
        while(st.hasMoreTokens()) {
            data.add(st.nextToken());
        }
        System.out.println("Data to decode : " + dt);
        ListIterator<String> it = data.listIterator();
        String toDecode = it.next();
        switch(toDecode) {
            case "CREATEFRAME":
                int nbMaxOfPlayers = Integer.parseInt(it.next());
                int chosenMap = Integer.parseInt(it.next());
                jgf = new PlayerServerMenu(chosenMap, nbMaxOfPlayers, isCreator, this) ;
                break;

            default:break;
        }*/
        for (DataElt d : dt.getList()) {
            if (d instanceof PlayerServerMenuCreator) {
                System.out.println("Je suis la data de création de frame");
                psm = ((PlayerServerMenuCreator) d).createPlayerServerMenu(this);
            }
            if(d instanceof PlayerServerMenuEditor) {
                ((PlayerServerMenuEditor)d).edit(psm);
            }
        }
    }




    public void sendDataElt(DataElt elt) {
        cnx.sendDataElt(elt);
    }
}
