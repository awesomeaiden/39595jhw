package types;

import game.PlayerMover;

public class BlessCurseOwner extends ItemAction {
    public BlessCurseOwner(Item _owner) {
        super(_owner);
        System.out.println("Creating BlessCurseOwner: " + _owner);
    }

    @Override
    public void activate(Player player, ObjectDisplayGrid odg, PlayerMover pm) {
    }
}
