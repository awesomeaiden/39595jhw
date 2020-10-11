public class EndGame extends CreatureAction {
    private String name;

    public EndGame(String _name, Creature _owner) {
        super(_owner);
        System.out.println("Creating an EndGame: " + _name + _owner);
        name = _name;
    }
}
