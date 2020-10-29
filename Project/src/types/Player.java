package types;

public class Player extends Creature {
    private Item weapon;
    private Item armor;

    public Player() {
        System.out.println("Creating a Player");
    }

    public void setWeapon(Item sword) {
        System.out.println("Setting weapon: " + sword);
        weapon = sword;
    }

    public void setArmor(Item armor) {
        System.out.println("Setting armor: " + armor);
        this.armor = armor;
    }

    @Override
    public char getChar() {
        return '@';
    }
}
