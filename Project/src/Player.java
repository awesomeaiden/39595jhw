public class Player extends Creature {
    private Item weapon;
    private Item armor;

    public Player() {

    }

    public void setWeapon(Item sword) {
        weapon = sword;
    }

    public void setArmor(Item armor) {
        this.armor = armor;
    }
}
