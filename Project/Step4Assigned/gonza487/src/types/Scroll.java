package types;

public class Scroll extends Item {
    public Scroll(String _name) {
        System.out.println("Creating a Scroll: " + _name);
        setName(_name);
        setType('?');
    }
}
