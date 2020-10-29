package types;

public class ItemAction extends Action {
    private Item owner;

    public ItemAction(Item _owner) {
        System.out.println("Creating an ItemAction: " + _owner);
        owner = _owner;
    }
}
