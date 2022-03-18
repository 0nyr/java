package utils;

public class Utils {

    public static long convertString2Long(String str) {
        if(str.length() == 0) return 0;
        byte[] bytes = str.getBytes();

        long value = 0;
        for(int i = 0; i < bytes.length; i++) {
            value += 10*i*(long)(bytes[i]);
        }

        return value;
    }
    
}
