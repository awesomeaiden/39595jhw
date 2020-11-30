package game;

import types.*;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PlayerMover implements InputObserver, MoveSubject, Runnable {

    private static int DEBUG = 1;
    private static String CLASSID = "PlayerMover";
    private static Queue<Character> inputQueue = null;
    private types.ObjectDisplayGrid displayGrid;
    private int posX;
    private int posY;
    private Player player;
    private boolean playerDead = false;
    private boolean helpMode = false; // mode to get help with commands
    private boolean readMode = false; // mode to read scroll
    private boolean takeMode = false; // mode to take out sword
    private boolean wearMode = false; // mode to wear armor
    private ArrayList<MoveObserver> moveObservers = new ArrayList<MoveObserver>();

    public PlayerMover(types.ObjectDisplayGrid grid, int _posX, int _posY, Player _player) {
        inputQueue = new ConcurrentLinkedQueue<>();
        displayGrid = grid;
        posX = _posX;
        posY = _posY;
        player = _player;
        registerMoveObserver(player);
    }

    @Override
    public void observerUpdate(char ch) {
        if (DEBUG > 1) {
            System.out.println(CLASSID + ".observerUpdate receiving character " + ch);
        }
        inputQueue.add(ch);
    }

    @Override
    public void registerMoveObserver(MoveObserver observer) {
        moveObservers.add(observer);
    }

    public void removeMoveObserver(MoveObserver observer) {
        moveObservers.remove(observer);
    }

    private void notifyMoveObservers(int moves) {
        for (MoveObserver observer : moveObservers) {
            observer.observerUpdate(moves);
        }
    }

    private void rest() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean processInput() {
        char ch;
        boolean processing = true;
        while (processing) {
            if (inputQueue.peek() == null) {
                processing = false;
            } else {
                ch = inputQueue.poll();
                if (DEBUG > 1) {
                    System.out.println(CLASSID + ".processInput peek is " + ch);
                }
                if (ch == 'X') {
                    System.out.println("got an X, ending input checking");
                    return false;
                } else if (playerDead) {
                    System.out.println("player is dead, game is over, input stopped!");
                    return false;
                } else {
                    if (helpMode) {
                        if (ch == 'h') {
                            displayGrid.displayInfo("h: Move player left");
                        } else if (ch == 'j') {
                            displayGrid.displayInfo("j: Move player down");
                        } else if (ch == 'k') {
                            displayGrid.displayInfo("k: Move player up");
                        } else if (ch == 'l') {
                            displayGrid.displayInfo("l: Move player right");
                        } else if (ch == 'p') {
                            displayGrid.displayInfo("p: Pick up item");
                        } else if (ch == 'd') {
                            displayGrid.displayInfo("d <integer>: drop item from pack indexed by <integer>");
                        } else if (ch == 'i') {
                            displayGrid.displayInfo("i: display pack contents");
                        } else if (ch == 'c') {
                            displayGrid.displayInfo("c: change (remove) armor");
                        } else if (ch == 'r') {
                            displayGrid.displayInfo("r <integer>: read (activate) scroll at index <integer> in pack");
                        } else if (ch == 'T') {
                            displayGrid.displayInfo("T <integer>: take out sword at index <integer> in pack");
                        } else if (ch == 'w') {
                            displayGrid.displayInfo("w <integer>: wear armor at index <integer> in pack");
                        } else if (ch == 'E') {
                            displayGrid.displayInfo("E: end the game");
                        } else if (ch == '?') {
                            displayGrid.displayInfo("?: show available commands");
                        } else if (ch == 'H') {
                            displayGrid.displayInfo("H <command>: show information about <command>");
                        }
                        helpMode = false;
                    } else if (readMode) {
                        if (48 <= ch && ch <= 57) {
                            displayGrid.displayInfo("Scroll activated!");
                            player.readScroll(ch - 49, displayGrid, this);
                        }
                        readMode = false;
                    } else if (takeMode) {
                        if (48 <= ch && ch <= 57) {
                            if (player.setWeapon(ch - 49)) {
                                displayGrid.displayInfo("Sword equipped!");
                            } else {
                                displayGrid.displayInfo("Item is not a sword!");
                            }
                        }
                        takeMode = false;
                    } else if (wearMode) {
                        if (48 <= ch && ch <= 57) {
                            if (player.setArmor(ch - 49)) {
                                displayGrid.displayInfo("Armor equipped!");
                            } else {
                                displayGrid.displayInfo("Item is not armor!");
                            }
                        }
                        wearMode = false;
                    } else {
                        if (ch == 'k' && posY > displayGrid.getTopHeight()) {
                            movePlayer(posX, posY - 1);
                        } else if (ch == 'h' && posX > 0) {
                            movePlayer(posX - 1, posY);
                        } else if (ch == 'j' && posY < displayGrid.getHeight() - 1) {
                            movePlayer(posX, posY + 1);
                        } else if (ch == 'l' && posX < displayGrid.getWidth() - 1) {
                            movePlayer(posX + 1, posY);
                        } else if (ch == 'p') {
                            pickupItem();
                        } else if (ch == 'd') {
                            displayGrid.displayPack(player.getPack());
                            displayGrid.displayInfo("Enter number of item to drop: ");
                        } else if (ch == 'i') {
                            displayGrid.displayPack(player.getPack());
                            if (player.packEmpty()) {
                                displayGrid.displayInfo("Pack empty!");
                            } else {
                                displayGrid.displayInfo("Pack displayed");
                            }
                        } else if (48 <= ch && ch <= 57) {
                            displayGrid.displayInfo("");
                            dropItem(ch - 49);
                        } else if (ch == 'c') {
                            changeArmor();
                        } else if (ch == 'r') {
                            displayGrid.displayInfo("Enter the pack index of the scroll you would like to read!");
                            readMode = true;
                        } else if (ch == 'T') {
                            displayGrid.displayInfo("Enter the pack index of the sword you would like to wield!");
                            takeMode = true;
                        } else if (ch == 'w') {
                            displayGrid.displayInfo("Enter the pack index of the armor you would like to wear!");
                            wearMode = true;
                        } else if (ch == 'E') {
                            System.out.println("Ending game!");
                            displayGrid.displayInfo("E pressed: Game ended!");
                            return false;
                        } else if (ch == '?') {
                            displayGrid.displayInfo("h j k l p d i c r T w E ? H");
                        } else if (ch == 'H') {
                            displayGrid.displayInfo("Enter the command you would like to learn about!");
                            helpMode = true;
                        }
                    }
                    if (DEBUG > 0) {
                        System.out.println("character " + ch + " entered on the keyboard");
                    }
                }
            }
        }
        return true;
    }

    private void changeArmor() {
        if (!player.changeArmor()) {
            displayGrid.displayInfo("No armor currently being worn!");
        } else {
            displayGrid.displayInfo("Armor taken off!");
        }
    }

    private void pickupItem() {
        if (displayGrid.getItemFromDisplay(player.getDispPosX(), player.getDispPosY()) instanceof Item) {
            Item item = (Item)displayGrid.removeItemFromDisplay(player.getDispPosX(), player.getDispPosY());
            if (player.addToPack(item) == -1) { // if can't pick up because pack is full
                displayGrid.displayInfo("Pack full!");
            }
            // TODO what to do about Hallucinate action? Need to register for movement observation
        }
    }

    private void dropItem(int index) {
        Item item = player.removeFromPack(index);
        if (item != null) {
            displayGrid.addItemToDisplay(item, player.getDispPosX(), player.getDispPosY());
        } else {
            displayGrid.displayInfo("Invalid item index!");
        }
    }

    public void movePlayer(int posX2, int posY2) {
        Object newPos = displayGrid.getObjectFromDisplay(posX2, posY2);
        char newPosChar = ((Displayable) newPos).getChar();
        notifyMoveObservers(1);
        if (newPos instanceof Monster) {
            attackMonster((Player)displayGrid.getObjectFromDisplay(posX, posY), (Monster)newPos);
        } else if (newPosChar != 'X' && newPosChar != ' ') {
            displayGrid.displayHp(player.getHp());
            displayGrid.removeObjectFromDisplay(posX, posY);
            displayGrid.addObjectToDisplay(player, posX2, posY2);
            posX = posX2;
            posY = posY2;
        }
    }

    public void attackMonster(Player player, Monster monster) {
        // Player attacks monster
        int playerDamage = player.hit(monster, displayGrid);
        if (monster.getHp() <= 0) {
            monster.die(displayGrid);
            displayGrid.removeObjectFromDisplay(monster.getDispPosX(), monster.getDispPosY());
            displayGrid.displayInfo("Player killed monster with attack for " + Integer.toString(playerDamage) + " damage!");
        } else {
            displayGrid.displayInfo("Player attacked monster for " + Integer.toString(playerDamage) + " damage!");
            try { // delay monster attack
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Monster attacks player
            int monsterDamage = monster.hit(player, displayGrid);
            if (player.getHp() <= 0) {
                player.die(displayGrid);
                displayGrid.removeObjectFromDisplay(player.getDispPosX(), player.getDispPosY());
                displayGrid.displayInfo("Monster killed player with attack for " + Integer.toString(monsterDamage) + " damage!");
                playerDead = true;
            } else {
                displayGrid.displayInfo("Monster attacked player for " + Integer.toString(monsterDamage) + " damage!");
            }
            displayGrid.displayHp(player.getHp());
        }
    }

    @Override
    public void run() {
        displayGrid.registerInputObserver(this);
        boolean working = true;
        while (working) {
            rest();
            working = (processInput( ));
        }
    }

}
