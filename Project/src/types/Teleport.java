package types;

import java.util.ArrayList;

public class Teleport extends CreatureAction {
    private String name;

    public Teleport(String _name, Creature _owner) {
        super(_owner);
        System.out.println("Creating a Teleport: " + _name + _owner);
        name = _name;
    }

    @Override
    public void activate(ObjectDisplayGrid odg) {
        Displayable space = odg.getTeleportSpace();
        odg.addObjectToDisplay(odg.removeObjectFromDisplay(getOwner().getDispPosX(), getOwner().getDispPosY()), space.getDispPosX(), space.getDispPosY());
    }
}
