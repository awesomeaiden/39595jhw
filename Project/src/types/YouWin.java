package types;

public class YouWin extends CreatureAction {
    private String name;

    public YouWin(String _name, Creature _owner) {
        super(_owner);
        System.out.println("Creating a YouWin: " + _name + _owner);
        name = _name;
    }

    @Override
    public void activate(ObjectDisplayGrid odg) {
        odg.displayInfo(getMessage());
    }
}
