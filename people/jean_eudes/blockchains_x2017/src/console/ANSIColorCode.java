package console;

public enum ANSIColorCode implements ANSIEnum {

    // 256 color font attributes
    GREY1_C ("7"),
    TURQUOISE_C ("43"),
    LIGHT_BLUE_C ("153"),
    RED_C ("196"),
    LIGHT_ORANGE_C ("216"),

    PASSED_C ("76"),
    FAILED_C ("197"),
    MISSED_C ("208"),
    DEBUG_C ("227");

    public final String str;

    ANSIColorCode(String str) {
        this.str = str;
    }
    
}
