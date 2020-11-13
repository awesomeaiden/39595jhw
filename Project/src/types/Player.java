package types;

import java.util.ArrayList;

public class Player extends Creature {
    private Item weapon;
    private Item armor;
    private ArrayList<Item> pack;

    public Player() {
        System.out.println("Creating a Player");
        pack = new ArrayList<Item>();
    }

    public void setWeapon(Item sword) {
        System.out.println("Setting weapon: " + sword);
        weapon = sword;
    }

    public void setArmor(Item armor) {
        System.out.println("Setting armor: " + armor);
        this.armor = armor;
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
