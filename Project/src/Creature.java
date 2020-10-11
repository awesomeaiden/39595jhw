public class Creature extends Displayable {
    private CreatureAction deathAction;
    private CreatureAction hitAction;

    public Creature() {

    }

    public void setDeathAction(CreatureAction da) {
        deathAction = da;
    }

    public void setHitAction(CreatureAction ha) {
        hitAction = ha;
    }
}
