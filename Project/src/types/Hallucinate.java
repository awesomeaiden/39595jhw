package types;

import game.MoveObserver;

public class Hallucinate extends ItemAction implements MoveObserver {
    private int moves;
    private boolean active = true;

    public Hallucinate(Item _owner) {
        super(_owner);
        System.out.println("Creating a Hallucinate: " + _owner);
    }

    @Override
    public void observerUpdate(int _moves) {
        moves += _moves;
        if (moves >= getIntValue()) {
            active = false;
        }
    }
}
