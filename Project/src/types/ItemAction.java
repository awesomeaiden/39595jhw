package types;

import game.PlayerMover;

public abstract class ItemAction extends Action {
    private Item owner;

    public ItemAction(Item _owner) {
        System.out.println("Creating an ItemAction: " + _owner);
        owner = _owner;
    }

    abstract public void activate(Player player, ObjectDisplayGrid odg, PlayerMover pm);
}
