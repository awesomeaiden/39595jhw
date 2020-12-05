package types;

public class ChangeDisplayedType extends CreatureAction {
    private String name;

    public ChangeDisplayedType(String _name, Creature _owner) {
        super(_owner);
        System.out.println("Creating ChangedDisplayType: " + _name + _owner);
        name = _name;
    }

    @Override
    public void activate(ObjectDisplayGrid odg) {
        getOwner().setType(getCharValue());
        odg.writeToTerminal(getOwner().getDispPosX(), getOwner().getDispPosY());
    }
}
