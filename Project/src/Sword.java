public class Sword extends Item {
    private String name;
    private String id;

    public Sword(String _name) {
        name = _name;
    }

    public void setID(int room, int serial) {
        id = room + "-" + serial;
    }
}
