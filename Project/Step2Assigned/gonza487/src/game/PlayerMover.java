package game;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PlayerMover implements InputObserver, Runnable {

    private static int DEBUG = 0;
    private static String CLASSID = "PlayerMover";
    private static Queue<Character> inputQueue = null;
    private types.ObjectDisplayGrid displayGrid;
    private int posX;
    private int posY;
    private Char oldChar = new Char('.');

    public PlayerMover(types.ObjectDisplayGrid grid, int _posX, int _posY) {
        inputQueue = new ConcurrentLinkedQueue<>();
        displayGrid = grid;
        posX = _posX;
        posY = _posY;
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
                } else {
                    if (ch == 'w' && posY > displayGrid.getTopHeight()) {
                        movePlayer(posX, posY - 1);
                    } else if (ch == 'a' && posX > 0) {
                        movePlayer(posX - 1, posY);
                    } else if (ch == 's' && posY < displayGrid.getHeight() - 1) {
                        movePlayer(posX, posY + 1);
                    } else if (ch == 'd' && posX < displayGrid.getWidth() - 1) {
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
        Char newPos = displayGrid.getObjectFromDisplay(posX2, posY2);
        if (DEBUG > 0) {
            System.out.println("Moving player from " + Integer.toString(posX) + " " + Integer.toString(posY));
            System.out.println("To " + Integer.toString(posX2) + " " + Integer.toString(posY2));
        }
        char newPosChar = newPos.getChar();
        if (newPosChar == '.' || newPosChar == '+' || newPosChar == '#') {
            displayGrid.addObjectToDisplay(oldChar, posX, posY);
            displayGrid.addObjectToDisplay(new Char('@'), posX2, posY2);
            posX = posX2;
            posY = posY2;
            oldChar = new Char(newPosChar);
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
