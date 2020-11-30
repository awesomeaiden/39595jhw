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
        Item item = player.removeFromPack(0);
        if (item != null) {
            odg.addItemToDisplay(item, player.getDispPosX(), player.getDispPosY());
        } else {
            odg.displayInfo("Invalid item index!");
        }
    }
}
