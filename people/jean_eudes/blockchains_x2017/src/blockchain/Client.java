package blockchain;

import console.ANSIColorCode;
import console.Console;
import utils.Utils;

public class Client {

    private final String nom;
    private int solde;

    public Client(String nom, int solde) {
        this.nom = nom;
        this.solde = solde;
    }

    public Client(Client client) {
        this.nom = client.nom;
        this.solde = client.solde;
    }

    public void ajouterMontant(int montant) {
        this.solde += montant;
        Console.printlnFgColor("Client (" + nom + ") : " + solde, ANSIColorCode.DEBUG_C);
    }

    public String toString() {
        return String.format("(%s, %d)", nom, solde);
    }

    public long hash() {
        long hash = 7;
        hash = 31*hash + Utils.convertString2Long(nom);
        hash = 31*hash + (long)(solde);

        Console.printlnFgColor("Client.hash() : " + hash, ANSIColorCode.DEBUG_C);

        return hash;
    }
    
}
