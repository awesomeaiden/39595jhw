package types;

import game.MoveObserver;
import game.PlayerMover;

public class Hallucinate extends ItemAction implements MoveObserver {
    private int moves = 0;
    private ObjectDisplayGrid odg = null;
    private PlayerMover pm = null;

    public Hallucinate(Item _owner) {
        super(_owner);
        System.out.println("Creating a Hallucinate: " + _owner);
    }

    @Override
    public void observerUpdate(int _moves) {
        moves += _moves;
        if (moves <= getIntValue() && odg != null) {
            odg.hallucinate();
        } else if (pm != null) {
            odg.stopHallucinate();
        }
    }

    @Override
    public void activate(Player player, ObjectDisplayGrid _odg, PlayerMover _pm) {
        odg = _odg;
        pm = _pm;
        pm.registerMoveObserver(this);
        odg.initializeHallucinateSpaces();
    }
}
