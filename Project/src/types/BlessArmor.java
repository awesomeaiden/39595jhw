package types;

import game.PlayerMover;

public class BlessArmor extends ItemAction {
    public BlessArmor(Item _owner) {
        super(_owner);
        System.out.println("Creating BlessArmor: " + _owner);
    }

    @Override
    public void activate(Player player, ObjectDisplayGrid odg, PlayerMover pm) {
        char val = getCharValue();
        // TODO fix this mess
        String displayString = "Scroll of";
        if (val == 'a') {
            player.setArmorVal(player.getArmorVal() + getIntValue());
            if (getIntValue() > 0) {
                displayString += " blessing";
                if (player.armorEquipped()) {
                    displayString += " has blessed " + player.getArmor().getName() + "! " +
                            Integer.toString(getIntValue()) + " added to its effectiveness!";
                } else {
                    displayString += " has done nothing because there is no armor equipped!";
                }
            } else {
                displayString += " cursing";
                if (player.armorEquipped()) {
                    displayString += " has cursed " + player.getArmor().getName() + "! " +
                            Integer.toString(getIntValue()) + " applied to its effectiveness!";
                } else {
                    displayString += " has done nothing because there is no armor equipped!";
                }
            }
        } else if (val == 'w') {
            player.setSwordDamage(player.getSwordDamage() + getIntValue());
            if (getIntValue() > 0) {
                displayString += " blessing";
                if (player.weaponEquipped()) {
                    displayString += " has blessed " + player.getWeapon().getName() + "! " +
                            Integer.toString(getIntValue()) + " added to its effectiveness!";
                } else {
                    displayString += " has done nothing because there is no weapon equipped!";
                }
            } else {
                displayString += " cursing";
                if (player.weaponEquipped()) {
                    displayString += " has cursed " + player.getWeapon().getName() + "! " +
                            Integer.toString(getIntValue()) + " applied to its effectiveness!";
                } else {
                    displayString += " has done nothing because there is no weapon equipped!";
                }
            }
        }
        odg.displayInfo(displayString);
    }
}
