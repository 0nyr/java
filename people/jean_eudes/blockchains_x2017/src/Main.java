import blockchain.Bloc;
import blockchain.Blockchain;
import blockchain.Client;
import blockchain.Transaction;
import console.ANSIColorCode;
import console.Console;

public class Main {

    public static Blockchain blockchain;
    public static Console console;

    public static void ajouteTransaction(Transaction transaction) {
        Bloc currentBloc;
        long hash;

        // cas où la blockchain est vide
        if(blockchain.LB.isEmpty()) {
            hash = 0;
        } else {
            hash = H(transaction, blockchain.LB.getLast().hash); 
        }

        currentBloc = new Bloc(transaction, hash);
        blockchain.LB.addLast(currentBloc);

        Console.printlnFgColor(blockchain.toString(), ANSIColorCode.DEBUG_C);
    }

    public static long H(Transaction transaction, long hPrecedent) {
        // simple hash implementation : https://www.baeldung.com/java-hashcode
        long hash = 7;
        hash = 31*hash + hPrecedent;
        hash = 31*hash + transaction.hash();

        Console.printlnFgColor("H.hash() : " + hash, ANSIColorCode.DEBUG_C);

        return hash;
    }

    // public static boolean estCorrecte() {

    // }
    public static void main(String []args) {

        // init
        blockchain = new Blockchain();
        console = new Console();

        // clients
        Client alice = new Client("Alice", 20000);
        Client bob = new Client("Bob", 10000);

        ajouteTransaction(new Transaction(bob, alice, 3000));
        ajouteTransaction(new Transaction(alice, bob, 400));

        Console.printlnFgColor(blockchain.toString(), ANSIColorCode.TURQUOISE_C);

    }
}