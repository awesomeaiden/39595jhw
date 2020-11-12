package game;

import types.Displayable;
import types.Monster;
import types.Player;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Random;

public class PlayerMover implements InputObserver, Runnable {

    private static int DEBUG = 0;
    private static String CLASSID = "PlayerMover";
    private static Queue<Character> inputQueue = null;
    private types.ObjectDisplayGrid displayGrid;
    private int posX;
    private int posY;
    private Char oldChar = new Char('.');
    private Random random = new Random();
    private Player player;
    private boolean playerDead = false;

    public PlayerMover(types.ObjectDisplayGrid grid, int _posX, int _posY, Player _player) {
        inputQueue = new ConcurrentLinkedQueue<>();
        displayGrid = grid;
        posX = _posX;
        posY = _posY;
        player = _player;
    }

    @Override
    public void observerUpdate(char ch) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".observerUpdate receiving character " + ch);
        }
        inputQueue.add(ch);
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
                    }
                    if (DEBUG > 0) {
                        System.out.println("character " + ch + " entered on the keyboard");
                    }
                }
            }
        }
        return true;
    }

    public void movePlayer(int posX2, int posY2) {
        Object newPos = displayGrid.getObjectFromDisplay(posX2, posY2);
        char newPosChar = ((Displayable) newPos).getChar();
        if (DEBUG > 0) {
            System.out.println("Moving player from " + Integer.toString(posX) + " " + Integer.toString(posY));
            System.out.println("To " + Integer.toString(posX2) + " " + Integer.toString(posY2));
        }
        if (newPosChar == '.' || newPosChar == '+' || newPosChar == '#') {
            displayGrid.removeObjectFromDisplay(posX, posY);
            displayGrid.addObjectToDisplay(player, posX2, posY2);
            posX = posX2;
            posY = posY2;
        } else if (newPos instanceof Monster) {
            attackMonster((Player)displayGrid.getObjectFromDisplay(posX, posY), (Monster)newPos);
        }

    }

    public void attackMonster(Player player, Monster monster) {
        // Player attacks monster
        int playerDamage = random.nextInt(player.getMaxHit() + 1);
        monster.setHp(monster.getHp() - playerDamage);
        if (monster.getHp() <= 0) {
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
            int monsterDamage = random.nextInt(monster.getMaxHit() + 1);
            player.setHp(player.getHp() - monsterDamage);
            if (player.getHp() <= 0) {
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
