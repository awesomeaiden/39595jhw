package types;

public class Action {
    private String msg;
    private int ival;
    private char cval;

    public Action() {
        System.out.println("Creating action");
    }

    public void setMessage(String msg) {
        System.out.println("Setting action message: " + msg);
        this.msg = msg;
    }

    public void setIntValue(int ival) {
        System.out.println("Setting action intvalue: " + ival);
        this.ival = ival;
    }

    public int getIntValue() {
        return ival;
    }

    public void setCharValue(char cval) {
        System.out.println("Setting action charvalue: " + cval);
        this.cval = cval;
    }
}
