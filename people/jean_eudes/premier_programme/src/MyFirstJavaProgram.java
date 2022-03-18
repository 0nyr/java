public class MyFirstJavaProgram {
    public static void main(String []args) {
        
        String RESET = "\033[0m";

        String BOLD = "\033[1m";
        String DIM = "\033[2m";
        String UNDERLINED = "\033[4m";
        String BLINK = "\033[5m";
        String REVERSE = "\033[7m";

        String LIGHT_ORANGE_COLOR = "\033[38;5;216m";
        String TURQUOISE_COLOR = "\033[38;5;43m";
        String LIGHT_BLUE_COLOR = "\033[38;5;153m";
        String RED_COLOR = "\033[38;5;196m";
        String GREY1_COLOR = "\033[38;5;7m";

        String PASSED_COLOR = "\033[38;5;76m";
        String FAILED_COLOR = "\033[38;5;197m";
        String MISSED_COLOR = "\033[38;5;208m";

        String PASSED = "\033[1;4;38;5;76m";
        String FAILED = "\033[1;5;38;5;197m";

        String str = String.format("%sHello World%s", FAILED, RESET);

        System.out.println(str); // prints Hello World
    }
}