package types;

import game.MoveObserver;

import java.util.ArrayList;

public class Item extends Displayable {
    private String name = "";
    private String id = "";
    private ArrayList<ItemAction> itemActions = new ArrayList<ItemAction>();
    private int intValue = 0;

    public Item() {
        System.out.println("Creating an Item");
    }

    public void setIntValue(int intval) {
        System.out.println("Setting intValue: " + intval);
        intValue = intval;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setName(String name) {
        System.out.println("Setting item name: " + name);
        this.name = name;
    }

    public void setID(int room, int serial) {
        System.out.println("Setting item ID: " + room + serial);
        id = room + "-" + serial;
    }

    public void addAction(ItemAction ia) {
        System.out.println("Adding ItemAction: " + ia);
        itemActions.add(ia);
    }

    public String getName() {
        return name;
    }

    public ArrayList<ItemAction> getActions() {
        return itemActions;
    }
}
