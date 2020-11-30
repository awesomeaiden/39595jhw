package types;
import game.MoveObserver;
import game.PlayerMover;

import java.util.ArrayList;

public class Player extends Creature implements MoveObserver  {
    private Sword weapon;
    private Armor armor;
    private ArrayList<Item> pack;
    private int moves = 0;

    public Player() {
        System.out.println("Creating a Player");
        pack = new ArrayList<Item>();
        setType('@');
    }

    @Override
    public void observerUpdate(int _moves) {
        moves += _moves;
        if (moves >= getHpMoves()) {
            setHp(getHp() + 1);
            moves -= getHpMoves();
        }
    }

    @Override
    public int getSwordDamage() {
        if (weaponEquipped()) {
            return weapon.getIntValue();
        } else {
            return 0;
        }
    }

    public void setSwordDamage(int val) {
        if (weaponEquipped()) {
            weapon.setIntValue(val);
        }
    }

    @Override
    public int getArmorVal() {
        if (armorEquipped()) {
            return armor.getIntValue();
        } else {
            return 0;
        }
    }

    public void setArmorVal(int val) {
        if (armorEquipped()) {
            armor.setIntValue(val);
        }
    }

    public boolean weaponEquipped() {
        return (weapon != null);
    }

    public boolean armorEquipped() {
        return (armor != null);
    }

    public boolean setWeapon(int sword) {
        System.out.println("Setting weapon...");
        Item newSword = getFromPack(sword);
        if (newSword instanceof Sword) {
            weapon = (Sword) newSword;
            weapon.setEquipped(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean setArmor(int _armor) {
        System.out.println("Setting armor...");
        Item newArmor = getFromPack(_armor);
        if (newArmor instanceof Armor) {
            armor = (Armor) newArmor;
            armor.setEquipped(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean readScroll(int _scroll, ObjectDisplayGrid odg, PlayerMover pm) {
        Item scroll = getFromPack(_scroll);
        if (scroll instanceof Scroll) {
            for (ItemAction action : scroll.getActions()) {
                action.activate(this, odg, pm);
            }
            return true;
        }
        return false;
    }

    public int addToPack(Item item) {
        if (pack.size() < 9) {
            pack.add(item);
            return pack.size();
        } else {
            return -1;
        }
    }

    public Item removeFromPack(int index) {
        Item removed = getFromPack(index);
        if (removed != null) {
            if (removed instanceof Sword) {
                changeWeapon();
            } else if (removed instanceof Armor) {
                changeArmor();
            }
            pack.remove(index);
        }
        return removed;
    }

    public Item getFromPack(int index) {
        if (pack.size() > index && index >= 0) {
            return pack.get(index);
        } else {
            return null;
        }
    }

    public ArrayList<Item> getPack() {
        return pack;
    }

    public boolean changeArmor() {
        if (armorEquipped()) {
            armor.setEquipped(false);
            armor = null;
            return true;
        } else {
            return false;
        }
    }

    public boolean changeWeapon() {
        if (weaponEquipped()) {
            weapon.setEquipped(false);
            weapon = null;
            return true;
        } else {
            return false;
        }
    }

    public boolean packEmpty() {
        return (pack.size() == 0);
    }

    public Armor getArmor() {
        return armor;
    }

    public Item getWeapon() {
        return weapon;
    }
}
