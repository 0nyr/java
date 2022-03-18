package blockchain;

import console.ANSIColorCode;
import console.Console;

public class Transaction {

    private final Client c1;
    private final Client c2;
    private final int montant;

    public Transaction(Client c1, Client c2, int montant) {
        // execute transaction first
        this.montant = montant;
        executer(c1, c2); 

        // keep copy of clients
        Client cpC1 = new Client(c1);
        Client cpC2 = new Client(c2);
        this.c1 = cpC1;
        this.c2 = cpC2;
    }

    public void executer(Client sender, Client receiver) {
        sender.ajouterMontant(-montant);
        receiver.ajouterMontant(montant);
    }

    public String toString() {
        return String.format("[%s, %s, %d]", 
            c1.toString(), c2.toString(), montant
        );
    }

    public long hash() {
        long hash = 7;
        hash = 31*hash + c1.hash();
        hash = 31*hash + c2.hash();
        hash = 31*hash + (long)(montant);

        Console.printlnFgColor("Transaction.hash() : " + hash, ANSIColorCode.DEBUG_C);

        return hash;
    }
    
}
