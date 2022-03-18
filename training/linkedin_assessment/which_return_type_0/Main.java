import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static String processFunction(Integer number, Function<Integer, String> lambda) {
        return lambda.apply(number);
    }

    public static void main(String[] args) {
        Integer number = 5;
        Function<Integer, String> lambda = (y) -> {
            return y % 2 == 0 ? "even" : "odd";
        };

        System.out.println(processFunction(number, lambda));
    }
}