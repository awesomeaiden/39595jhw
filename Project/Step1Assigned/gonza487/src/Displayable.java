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
    private int width;
    private int height;

    public Displayable() {
        System.out.println("Creating Displayable");
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

    public void setWidth(int width) {
        System.out.println("Setting width: " + width);
        this.width = width;
    }

    public void setHeight(int height) {
        System.out.println("Setting height: " + height);
        this.height = height;
    }
}
