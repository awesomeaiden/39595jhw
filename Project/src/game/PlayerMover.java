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
                        dropItem(0);
                    } else if (ch == 'i') {
                        displayGrid.displayPack(player.getPack());
                    } else if (48 <= ch && ch <= 57) {
                        dropItem(ch - 48);
                    }
                    if (DEBUG > 0) {
                        System.out.println("character " + ch + " entered on the keyboard");
                    }
                }
            }
        }
        return true;
    }

    private void pickupItem() {
        if (displayGrid.getItemFromDisplay(player.getDispPosX(), player.getDispPosY()) instanceof Item) {
            Item item = (Item)displayGrid.removeItemFromDisplay(player.getDispPosX(), player.getDispPosY());
            player.addToPack(item);
            if (item.getAction() instanceof Hallucinate) {
                registerMoveObserver((Hallucinate)item.getAction());
            }
        }
    }

    private void dropItem(int index) {
        Item item = player.removeFromPack(index);
        displayGrid.addItemToDisplay(item, player.getDispPosX(), player.getDispPosY());
    }

    public void movePlayer(int posX2, int posY2) {
        Object newPos = displayGrid.getObjectFromDisplay(posX2, posY2);
        char newPosChar = ((Displayable) newPos).getChar();
        if (newPos instanceof Monster) {
            attackMonster((Player)displayGrid.getObjectFromDisplay(posX, posY), (Monster)newPos);
        } else if (newPosChar != 'X' && newPosChar != ' ') {
            notifyMoveObservers(1);
            displayGrid.displayHp(player.getHp());
            displayGrid.removeObjectFromDisplay(posX, posY);
            displayGrid.addObjectToDisplay(player, posX2, posY2);
            posX = posX2;
            posY = posY2;
        }
    }

    public void attackMonster(Player player, Monster monster) {
        // Player attacks monster
        int playerDamage = monster.hit(player);
        if (monster.getHp() <= 0) {
            monster.die();
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
            int monsterDamage = player.hit(monster);
            if (player.getHp() <= 0) {
                player.die();
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
