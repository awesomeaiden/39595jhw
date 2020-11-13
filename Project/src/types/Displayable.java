package types;

import com.sun.security.jgss.GSSUtil;

public class Displayable {
    // Displayable attributes
    private boolean visible;
    private int maxHit;
    private int hpMoves;
    private int hp;
    private char type;
    private int value;
    private int posX;
    private int posY;
    private int dispPosX;
    private int dispPosY;
    private int width;
    private int height;
    private static boolean DEBUG = false;

    public Displayable() {
        if (DEBUG) {
            System.out.println("Creating Displayable");
        }
    }

    public void setInvisible() {
        System.out.println("Setting invisible");
        visible = false;
    }

    public void setVisible() {
        System.out.println("Setting visible");
        visible = true;
    }

    public void setMaxHit(int maxHit) {
        System.out.println("Setting MaxHit: " + maxHit);
        this.maxHit = maxHit;
    }

    public void setHpMoves(int hpMoves) {
        System.out.println("Setting HpMoves: " + hpMoves);
        this.hpMoves = hpMoves;
    }

    public void setHp(int hp) {
        System.out.println("Setting Hp: " + hp);
        this.hp = hp;
    }

    public void setType(char type) {
        System.out.println("Setting type: " + type);
        this.type = type;
    }

    public void setValue(int value) {
        System.out.println("Setting value: " + value);
        this.value = value;
    }

    public void setPosX(int posX) {
        System.out.println("Setting PosX: " + posX);
        this.posX = posX;
    }

    public void setPosY(int posY) {
        System.out.println("Setting PosY: " + posY);
        this.posY = posY;
    }

    public void setDispPosX(int dispPosX) {
        if (DEBUG) {
            System.out.println("Setting DispPosX: " + dispPosX);
        }
        this.dispPosX = dispPosX;
    }

    public void setDispPosY(int dispPosY) {
        if (DEBUG) {
            System.out.println("setting DispPosY: " + dispPosY);
        }
        this.dispPosY = dispPosY;
    }

    public void setWidth(int width) {
        System.out.println("Setting width: " + width);
        this.width = width;
    }

    public void setHeight(int height) {
        System.out.println("Setting height: " + height);
        this.height = height;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getMaxHit() {
        return maxHit;
    }

    public int getHpMoves() {
        return hpMoves;
    }

    public int getHp() {
        return hp;
    }

    public char getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getDispPosX() {
        return dispPosX;
    }

    public int getDispPosY() {
        return dispPosY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char getChar() {
        return getType();
    }
}
