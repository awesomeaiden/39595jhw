package types;

import game.MoveObserver;
import java.util.Random;

import java.util.ArrayList;

public class Creature extends Displayable {
    private ArrayList<CreatureAction> deathActions = new ArrayList();
    private ArrayList<CreatureAction> hitActions = new ArrayList();
    private String name;
    private String id;
    private Random random = new Random();

    public Creature() {
        System.out.println("Creating creature");
    }

    public void setName(String name) {
        System.out.println("Setting name: " + name);
        this.name = name;
    }

    public void setID(int room, int serial) {
        System.out.println("Setting ID: " + room + serial);
        id = room + "-" + serial;
    }

    public void addDeathAction(CreatureAction da) {
        System.out.println("Setting DeathAction: " + da);
        deathActions.add(da);
    }

    public void addHitAction(CreatureAction ha) {
        System.out.println("Setting HitAction: " + ha);
        hitActions.add(ha);
    }

    public int hit(Creature by) {
        int damage = random.nextInt(by.getMaxHit() + 1);
        setHp(getHp() - damage);
        for (CreatureAction action : hitActions) {
            action.activate();
        }
        return damage;
    }

    public void die() {
        for (CreatureAction action : deathActions) {
            action.activate();
        }
    }
}
