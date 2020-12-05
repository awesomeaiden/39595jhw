package types;

public class Remove extends CreatureAction {
    private String name;

    public Remove(String _name, Creature _owner) {
        super(_owner);
        System.out.println("Creating a Remove: " + _name + _owner);
        name = _name;
    }

    @Override
    public void activate(ObjectDisplayGrid odg) {
        odg.removeItemFromDisplay(getOwner().getDispPosX(), getOwner().getDispPosY());
    }
}
