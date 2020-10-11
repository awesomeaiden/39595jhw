public class Armor extends Item {
    private String name;
    private String id;

    public Armor(String _name) {
        name = _name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int room, int serial) {
        id = room + "-" + serial;
    }
}
