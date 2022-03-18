package console;

public class Console {

    private static final String reset = ANSICtrlSequence.RESET.str;

    // foreground
    public static void printlnFgColor(String message, ANSIColorCode colorCode) {
        String ctrlSequence = String.format("%s%s%sm", 
            ANSIString.ESC.str, ANSIString.FG_256.str, colorCode.str
        );
        System.out.println(String.format("%s%s%s", ctrlSequence, message, reset));
    }

    public static void printlnCtrlSequence(String message, ANSICtrlSequence ctrlSequence) {
        System.out.println(String.format("%s%s%s", ctrlSequence.str, message, reset));
    }

    public static void printlnCtrlSequence(String message, String ctrlSequenceStr) {
        System.out.println(String.format("%s%s%s", ctrlSequenceStr, message, reset));
    }
    
}
