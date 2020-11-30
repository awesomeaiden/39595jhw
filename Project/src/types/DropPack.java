package types;

public class DropPack extends CreatureAction {
    private String name;

    public DropPack(String _name, Creature _owner) {
        super(_owner);
        System.out.println("Creating DropPack: " + _name + _owner);
        name = _name;
    }

    @Override
    public void activate(ObjectDisplayGrid odg) {
        Player player = (Player) getOwner();
        player.removeFromPack(0);
    }
}
