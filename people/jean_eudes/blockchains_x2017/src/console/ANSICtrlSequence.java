package console;

public enum ANSICtrlSequence implements ANSIEnum {

    // basic control sequences
    RESET ("\033[m"),

    // full control sequences
    PASSED ("\033[1;4;38;5;76m"),
    FAILED ("\033[1;5;38;5;197m"),

    // substitution control sequences
    FG_CRTL_S ("\033[38;5;%sm"),
    BG_CRTL_S ("\033[48;5;%sm");

    public final String str;

    ANSICtrlSequence(String str) {
        this.str = str;
    }
 
}
