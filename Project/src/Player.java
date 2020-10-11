public class Player extends Creature {
    private Item weapon;
    private Item armor;
    private int hpMoves;

    public Player() {
        System.out.println("Creating a Player");
    }

    public void setHPMoves(int hpMoves) {
        System.out.println("Setting hpMoves: " + hpMoves);
        this.hpMoves = hpMoves;
    }

    public void setWeapon(Item sword) {
        System.out.println("Setting weapon: " + sword);
        weapon = sword;
    }

    public void setArmor(Item armor) {
        System.out.println("Setting armor: " + armor);
        this.armor = armor;
    }
}
