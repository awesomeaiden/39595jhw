import java.util.ArrayList;

public class Room extends Structure {
    // Room attributes
    private int id;
    private ArrayList<Creature> creatures = new ArrayList();
    private ArrayList<Item> items = new ArrayList();

    public Room(String _name) {
        System.out.println("Creating a Room: " + _name);
        setName(_name);
    }

    public void setID(int room1) {
        System.out.println("Setting ID: " + room1);
        id = room1;
    }

    public void addCreature(Creature creature) {
        System.out.println("Adding creature: " + creature);
        creatures.add(creature);
    }

    public void addItem(Item item) {
        System.out.println("Adding item to room: " + item);
        items.add(item);
    }
}
