import java.util.ArrayList;

public class Room extends Structure {
    // Room attributes
    private String name;
    private int id;
    private ArrayList<Creature> creatures = new ArrayList();

    public Room(String _name) {
        name = _name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreature(Creature creature) {
        creatures.add(creature);
    }
}
