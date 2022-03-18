package blockchain;

public class Bloc {

    public final Transaction transaction;
    public final long hash;

    public Bloc(Transaction transaction, long hash) {
        this.transaction = transaction;
        this.hash = hash;
    }

    public String toString() {
        return String.format("(%s, %d)", transaction.toString(), hash);
    }
    
    
}
