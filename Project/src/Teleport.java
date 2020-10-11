public class Teleport extends CreatureAction {
    private String name;

    public Teleport(String _name, Creature _owner) {
        super(_owner);
        System.out.println("Creating a Teleport: " + _name + _owner);
        name = _name;
    }
}
