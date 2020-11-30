package types;

public class EmptyPack extends CreatureAction {
    private String name;

    public EmptyPack(String _name, Creature _owner) {
        super(_owner);
        System.out.println("Creating EmptyPack: " + _name + _owner);
        name = _name;
    }

    @Override
    public void activate(ObjectDisplayGrid odg) {
        Player player = (Player) getOwner();
        while (!player.packEmpty()) {
            player.removeFromPack(0);
        }
    }
}
