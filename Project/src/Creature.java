public class Creature extends Displayable {
    private CreatureAction deathAction;
    private CreatureAction hitAction;
    private String name;
    private String id;
    private int hp;
    private int maxHit;
    private char type;

    public Creature() {
        System.out.println("Creating creature");
    }

    public void setMaxHit(int maxHit) {
        System.out.println("Setting maxHit: " + maxHit);
        this.maxHit = maxHit;
    }

    public void setHP(int hp) {
        System.out.println("Setting hp: " + hp);
        this.hp = hp;
    }

    public void setName(String name) {
        System.out.println("Setting name: " + name);
        this.name = name;
    }

    public void setType(char t) {
        System.out.println("Setting type: " + t);
        type = t;
    }

    public void setID(int room, int serial) {
        System.out.println("Setting ID: " + room + serial);
        id = room + "-" + serial;
    }

    public void setDeathAction(CreatureAction da) {
        System.out.println("Setting DeathAction: " + da);
        deathAction = da;
    }

    public void setHitAction(CreatureAction ha) {
        System.out.println("Setting HitAction: " + ha);
        hitAction = ha;
    }
}
