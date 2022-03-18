// Importing interface
import java.util.function.Function;

public class Main {

    // Which is a valid type for this lambda function?
    public static Function<Integer, String> oddOrEven = (x) -> {
        return x % 2 == 0 ? "even" : "odd";
    };

    public static void main(String []args) {
        
        // test lambda
        int x = 5;
        System.out.println(oddOrEven.apply(x));

    }
}