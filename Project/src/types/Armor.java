package types;

public class Armor extends Item {
    public Armor(String _name) {
        System.out.println("Creating armor: " + _name);
        this.setName(_name);
    }

    @Override
    public char getChar() {
        return ']';
    }
}
