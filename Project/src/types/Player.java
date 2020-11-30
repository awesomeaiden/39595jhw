package types;
import game.InputObserver;
import game.MoveObserver;

import java.util.ArrayList;

public class Player extends Creature implements MoveObserver  {
    private Item weapon;
    private Item armor;
    private ArrayList<Item> pack;
    private int moves = 0;

    public Player() {
        System.out.println("Creating a Player");
        pack = new ArrayList<Item>();
    }

    @Override
    public void observerUpdate(int _moves) {
        moves += _moves;
        if (moves >= getHpMoves()) {
            setHp(getHp() + 1);
            moves -= getHpMoves();
        }
    }

    public void setWeapon(Item sword) {
        System.out.println("Setting weapon: " + sword);
        weapon = sword;
        boolean swordInPack = false;
        for (int i = 0; i < pack.size(); i++) {
            if (pack.get(i) instanceof Sword) {
                swordInPack = true;
            }
        }
        if (!swordInPack) {
            addToPack(sword);
        }
    }

    public void setArmor(Item armor) {
        System.out.println("Setting armor: " + armor);
        this.armor = armor;
        boolean armorInPack = false;
        for (int i = 0; i < pack.size(); i++) {
            if (pack.get(i) instanceof Armor) {
                armorInPack = true;
            }
        }
        if (!armorInPack) {
            addToPack(armor);
        }
    }

    public void addToPack(Item item) {
        pack.add(item);
    }

    public Item removeFromPack(int index) {
        Item removed = pack.get(index);
        pack.remove(index);
        return removed;
    }

    @Override
    public char getChar() {
        return '@';
    }

    public ArrayList<Item> getPack() {
        return pack;
    }
}
