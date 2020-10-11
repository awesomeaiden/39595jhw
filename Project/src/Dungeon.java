import java.util.ArrayList;

public class Dungeon {
    // Dungeon private attributes
    private String name;
    private int width;
    private int gameHeight;
    private static Dungeon dungeon = null;

    // HASA object lists
    private ArrayList<Room> rooms = new ArrayList();
    private ArrayList<Creature> creatures = new ArrayList();
    private ArrayList<Passage> passages = new ArrayList();
    private ArrayList<Item> items = new ArrayList();

    private Dungeon(String _name, int _width, int _gameHeight) {
        System.out.println("Creating dungeon: " + _name + _width + _gameHeight);
        name = _name;
        width = _width;
        gameHeight = _gameHeight;
    }

    // Used by code outside the class to get a dungeon
    public Dungeon getDungeon(String name, int width, int gameHeight) throws Exception {
        System.out.println("Getting dungeon...");
        if (dungeon == null) { // create dungeon with name, width, and gameHeight
            System.out.println("Dungeon has not been created yet...");
            dungeon = new Dungeon(name, width, gameHeight);
            return dungeon;
        } else if (dungeon.isSame(name, width, gameHeight)) {
            System.out.println("Matching dungeon exists...");
            return dungeon;
        } else {
            throw new Exception("Dungeon specs don't match!");
        }
    }

    public boolean isSame(String _name, int _width, int _gameHeight) {
        System.out.println("Checking if dungeon specs match current...");
        return (name == _name && width == _width && gameHeight == _gameHeight);
    }

    public void addRoom(Room room) {
        System.out.println("Adding a room");
        rooms.add(room);
    }

    public void addCreature(Creature creature) {
        System.out.println("Adding a creature");
        creatures.add(creature);
    }

    public void addPassage(Passage passage) {
        System.out.println("Adding a passage");
        passages.add(passage);
    }

    public void addItem(Item item) {
        System.out.println("Adding an item");
        items.add(item);
    }
}
