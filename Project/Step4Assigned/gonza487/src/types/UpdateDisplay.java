package types;

public class UpdateDisplay extends CreatureAction {
    private String name;

    public UpdateDisplay(String _name, Creature _owner) {
        super(_owner);
        System.out.println("Creating an UpdateDisplay: " + _name + _owner);
        name = _name;
    }

    @Override
    public void activate(ObjectDisplayGrid odg) {
        odg.writeToTerminal(getOwner().getDispPosX(), getOwner().getDispPosY());
        if (getOwner() instanceof Player) {
            odg.displayHp(getOwner().getHp());
        }
    }
}
