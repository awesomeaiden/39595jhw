public class Monster extends Creature {
    private String name;
    private String id;

    public Monster() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int room, int serial) {
        id = room + "-" + serial;
    }
}
