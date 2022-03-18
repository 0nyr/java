import console.ANSIColorCode;
import console.ANSICtrlSequence;
import console.ANSIString;
import console.Console;
import utils.Utils;

public class MainTest {
    public static void main(String []args) {

        // test console
        Console.printlnCtrlSequence("*** Running tests ! ***", 
            String.format("%s%s;%s;%s;%s%s%s", 
                ANSIString.ESC.str, 
                ANSIString.BOLD.str, 
                ANSIString.UNDERLINED.str,
                ANSIString.BLINK.str,
                ANSIString.FG_256.str, 
                ANSIColorCode.MISSED_C.str,
                ANSIString.END.str
            )
        );

        Console.printlnFgColor("Hello World", ANSIColorCode.TURQUOISE_C);
        Console.printlnCtrlSequence("Hello World", ANSICtrlSequence.FAILED);
        Console.printlnCtrlSequence("Hello World", "\033[1;4m");

        // test utils
        System.out.println(
            String.format("hello world = %d", Utils.convertString2Long("hello world"))
        );
        System.out.println(
            String.format("onyr = %d", Utils.convertString2Long("onyr"))
        );
        System.out.println(
            String.format("il était une fois 42 = %d", Utils.convertString2Long("il était une fois 42"))
        );

        // test concat
        String test1Str = "lolilol ";
test1Str.concat(" trolollol");
        System.out.println("test1Str = " + test1Str);

    }
}