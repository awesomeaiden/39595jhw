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

    }

    public void setInvisible() {
        visible = false;
    }

    public void setVisible() {
        visible = true;
    }

    public void setMaxHit(int maxHit) {
        this.maxHit = maxHit;
    }

    public void setHpMoves(int hpMoves) {
        this.hpMoves = hpMoves;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setType(char type) {
        this.type = type;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
