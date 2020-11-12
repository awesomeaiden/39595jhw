package types;

public class CreatureAction extends Action {
    private Creature owner;

    public CreatureAction(Creature _owner) {
        System.out.println("Creating CreatureAction: " + _owner);
        owner = _owner;
    }
}
