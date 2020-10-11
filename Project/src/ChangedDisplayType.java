public class ChangedDisplayType extends CreatureAction {
    private String name;

    public ChangedDisplayType(String _name, Creature _owner) {
        super(_owner);
        System.out.println("Creating ChangedDisplayType: " + _name + _owner);
        name = _name;
    }
}
