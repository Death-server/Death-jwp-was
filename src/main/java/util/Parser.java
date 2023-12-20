package util;

public class Parser {

    private static final String BLANK = " ";
    public static String[] parseRequestHeader(String header) {
        return header.split(BLANK);
    }
}
