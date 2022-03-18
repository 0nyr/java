package blockchain;

import java.util.LinkedList;

public class Blockchain {

    public LinkedList<Bloc> LB;

    public Blockchain() {
        LB = new LinkedList<Bloc>();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("blockchain : ");
        int size = LB.size();

        if(size <= 0) return sb.toString();

        for(Bloc bloc : LB) {
            sb.append(bloc.toString());
            sb.append(" -> ");
        }
        sb.append(" null ");

        return sb.toString();
    }
    
}
