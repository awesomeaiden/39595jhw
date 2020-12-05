package types;

public class Armor extends Item {
    private boolean equipped = false;

    public Armor(String _name) {
        System.out.println("Creating armor: " + _name);
        setName(_name);
        setType(']');
    }

    public void setEquipped(boolean val) {
        equipped = val;
    }

    @Override
    public String getName() {
        String displayName = "";
        if (equipped) {
            displayName += "(a) ";
        }
        displayName += super.getName();
        displayName += " [" + Integer.toString(getIntValue()) + "]";
        return displayName;
    }
}
