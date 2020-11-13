package game;

import types.Displayable;

public class Char extends Displayable {

    public static final String CLASSID = "Char";
    private final char displayChar;

    public Char(char c) {
        displayChar = c;
    }

    @Override
    public char getChar( ) {
        return displayChar;
    }
}
