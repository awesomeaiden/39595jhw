package types;

public class Sword extends Item {
    private boolean equipped = false;

    public Sword(String _name) {
        System.out.println("Creating a Sword: " + _name);
        setName(_name);
        setType(')');
    }

    public void setEquipped(boolean val) {
        equipped = val;
    }

    @Override
    public String getName() {
        String displayName = "";
        if (equipped) {
            displayName += "(w) ";
        }
        displayName += super.getName();
        displayName += " [" + Integer.toString(getIntValue()) + "]";
        return displayName;
    }
}
