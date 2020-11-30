package types;

import asciiPanel.AsciiPanel;
import game.Char;
import game.InputObserver;
import game.InputSubject;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

import static java.lang.Math.round;

public class ObjectDisplayGrid extends JFrame implements KeyListener, InputSubject {
    // ObjectDisplayGrid private attributes
    private int gameHeight;
    private int width;
    private int height;
    private int topHeight;
    private int bottomHeight;
    private static ObjectDisplayGrid odg = null;

    private static final int DEBUG = 0;
    private static final String CLASSID = "ObjectDisplayGrid";

    private static AsciiPanel terminal;
    private Stack[][] objectGrid;

    private List<InputObserver> inputObservers = null;
    private boolean gameOver = false;

    private Random random = new Random();

    private ArrayList<Char> hallucinateChars = new ArrayList<Char>();
    ArrayList<Displayable> hallucinateSpaces = new ArrayList<Displayable>();

    private ObjectDisplayGrid(int _gameHeight, int _width, int _topHeight, int _bottomHeight) {
        System.out.println("Creating an ObjectDisplayGrid: " + _gameHeight + _width + _topHeight + _bottomHeight);
        gameHeight = _gameHeight;
        width = _width;
        topHeight = _topHeight;
        bottomHeight = _bottomHeight;
        setHeight();

        terminal = new AsciiPanel(width, height);
        objectGrid = new Stack[width][height];
        for (int i = 0; i < objectGrid.length; i++) {
            for (int j = 0; j < objectGrid[0].length; j++) {
                objectGrid[i][j] = new Stack();
            }
        }

        initializeDisplay();

        super.add(terminal);
        super.setSize((int)round(width * 9.25), height * 17);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
        terminal.setVisible(true);
        super.addKeyListener(this);
        inputObservers = new ArrayList<>();
        super.repaint();

        initializeHallucinateChars();
    }

    private void initializeHallucinateChars() {
        hallucinateChars.add(new Char('.'));
        hallucinateChars.add(new Char('#'));
        hallucinateChars.add(new Char('+'));
        hallucinateChars.add(new Char('X'));
        hallucinateChars.add(new Char(']'));
        hallucinateChars.add(new Char(')'));
        hallucinateChars.add(new Char('?'));
        hallucinateChars.add(new Char('T'));
        hallucinateChars.add(new Char('S'));
        hallucinateChars.add(new Char('@'));
    }

    public void initializeHallucinateSpaces() {
        Displayable space = null;
        char spaceChar;
        for (int x = 0; x < objectGrid.length; x++) {
            for (int y = 0; y < objectGrid[0].length; y++) {
                space = getObjectFromDisplay(x, y);
                spaceChar = space.getChar();
                if (hCharsContains(spaceChar)) {
                    hallucinateSpaces.add(space);
                }
            }
        }
    }

    @Override
    public void registerInputObserver(InputObserver observer) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".registerInputObserver " + observer.toString());
        }
        inputObservers.add(observer);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".keyTyped entered" + e.toString());
        }
        KeyEvent keypress = (KeyEvent) e;
        notifyInputObservers(keypress.getKeyChar());
    }

    private void notifyInputObservers(char ch) {
        if (!gameOver) {
            for (InputObserver observer : inputObservers) {
                observer.observerUpdate(ch);
                if (DEBUG > 0) {
                    System.out.println(CLASSID + ".notifyInputObserver " + ch);
                }
            }
        }
    }

    public void setGameOver(boolean val) {
        gameOver = val;
    }

    // we have to override, but we don't use this
    @Override
    public void keyPressed(KeyEvent even) {
    }

    // we have to override, but we don't use this
    @Override
    public void keyReleased(KeyEvent e) {
    }

    public final void initializeDisplay() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                addObjectToDisplay(new Char(' '), i, j);
            }
        }
        terminal.repaint();
    }

    public void fireUp() {
        if (terminal.requestFocusInWindow()) {
            System.out.println(CLASSID + ".ObjectDisplayGrid(...) requestFocusInWindow Succeeded");
        } else {
            System.out.println(CLASSID + ".ObjectDisplayGrid(...) requestFocusInWindow FAILED");
        }
    }

    public void addObjectToDisplay(Displayable obj, int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                obj.setDispPosX(x);
                obj.setDispPosY(y);
                objectGrid[x][y].push(obj);
                writeToTerminal(x, y);
            }
        }
    }

    public void addItemToDisplay(Item item, int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                item.setDispPosX(x);
                item.setDispPosY(y);
                Displayable keep = (Displayable) objectGrid[x][y].pop();
                objectGrid[x][y].push(item);
                objectGrid[x][y].push(keep);
                writeToTerminal(x, y);
            }
        }
    }

    public Displayable removeObjectFromDisplay(int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                if (objectGrid[x][y].size() > 1) {
                    Displayable remove = (Displayable) objectGrid[x][y].pop();
                    writeToTerminal(x, y);
                    return remove;
                }
            }
        }
        return new Char(' ');
    }

    // As opposed to removeObjectFromDisplay, this method removes the item underneath the player
    // so that the player can add it to its pack
    public Object removeItemFromDisplay(int x, int y) {
        Displayable keep = null;
        Displayable remove = null;
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                if (objectGrid[x][y].size() > 1) {
                    keep = (Displayable) objectGrid[x][y].pop();
                    if (objectGrid[x][y].size() > 1) {
                        remove = (Displayable) objectGrid[x][y].pop();
                    }
                    objectGrid[x][y].push(keep);
                    writeToTerminal(x, y);
                    return remove;
                }
            }
        }
        return new Char(' ');
    }

    public Displayable getObjectFromDisplay(int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                return (Displayable)objectGrid[x][y].peek();
            }
        }
        return new Char('0');
    }

    public Item getItemFromDisplay(int x, int y) {
        Item get = null;
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                if (objectGrid[x][y].size() > 1) {
                    Displayable keep = (Displayable) objectGrid[x][y].pop();
                    if (objectGrid[x][y].size() > 1) {
                        get = (Item) objectGrid[x][y].peek();
                    }
                    objectGrid[x][y].push(keep);
                    writeToTerminal(x, y);
                }
            }
        }
        return get;
    }

    public void writeToTerminal(int x, int y) {
        char ch = ((Displayable)objectGrid[x][y].peek()).getChar();
        terminal.write(ch, x, y);
        terminal.repaint();
    }

    public void displayPack(ArrayList<Item> pack) {
        String packString = "Pack: ";
        if (pack.size() > 0) {
            int i;
            for (i = 0; i < pack.size() - 1; i++) {
                packString = packString + Integer.toString(i + 1) + ": " + pack.get(i).getName() + ", ";
            }
            // Last item
            packString = packString + Integer.toString(i + 1) + ": " + pack.get(i).getName();
        }
        displayLine(packString, getHeight() - 3);
    }

    public void displayInfo(String info) {
        String infoString = "Info: " + info;
        displayLine(infoString, getHeight() - 1);
    }

    public void displayHp(int hp) {
        String hpString = "HP: " + Integer.toString(hp);
        displayWord(hpString, 0);
    }

    public int displayWord(String string, int yPos) {
        int col;
        for (col = 0; col < string.length(); col++) {
            removeObjectFromDisplay(col, yPos);
            addObjectToDisplay(new Char(string.charAt(col)), col, yPos);
            try { // give asciipanel some time, fixes missing characters issue
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return col;
    }

    public void displayLine(String string, int yPos) {
        int col = displayWord(string, yPos);
        while (col < objectGrid.length) {
            removeObjectFromDisplay(col, yPos);
            col++;
        }
    }

    public boolean hCharsContains(char spaceChar) {
        for (Char hChar : hallucinateChars) {
            if (hChar.getChar() == spaceChar) {
                return true;
            }
        }
        return false;
    }

    public Char getRandHChar() {
        return hallucinateChars.get(random.nextInt(hallucinateChars.size()));
    }

    public void hallucinate() {
        for (Displayable changeSpace : hallucinateSpaces) {
            changeSpace.setHallucinate(true);
            char newChar = getRandHChar().getChar();
            changeSpace.setHChar(newChar);
            terminal.write(newChar, changeSpace.getDispPosX(), changeSpace.getDispPosY());
            terminal.repaint();
        }
    }

    public void stopHallucinate() {
        for (Displayable changeSpace : hallucinateSpaces) {
            changeSpace.setHallucinate(false);
            writeToTerminal(changeSpace.getDispPosX(), changeSpace.getDispPosY());
        }
    }

    // Used by code outside the class to get a dungeon
    public static ObjectDisplayGrid getObjectDisplayGrid(int gameHeight, int width, int topHeight, int bottomHeight) throws Exception {
        System.out.println("Getting an ObjectDisplayGrid...");
        if (odg == null) { // create dungeon with name, width, and gameHeight
            System.out.println("ObjectDisplayGrid not created yet...");
            odg = new ObjectDisplayGrid(gameHeight, width, topHeight, bottomHeight);
            return odg;
        } else if (odg.isSame(gameHeight, width, topHeight, bottomHeight)) {
            System.out.println("Matching ObjectDisplayGrid already created...");
            return odg;
        } else {
            throw new Exception("ObjectDisplayGrid specs don't match!");
        }
    }

    public boolean isSame(int _gameHeight, int _width, int _topHeight, int _bottomHeight) {
        System.out.println("Checking if ObjectDisplayGrid specs match current...");
        return (gameHeight == _gameHeight && width == _width && topHeight == _topHeight && bottomHeight == _bottomHeight);
    }

    public void setHeight() {
        height = gameHeight + topHeight + bottomHeight;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public int getTopHeight() {
        return topHeight;
    }

    public int getBottomHeight() {
        return bottomHeight;
    }

    public Displayable getTeleportSpace() {
        ArrayList<Displayable> spaces = new ArrayList<Displayable>();
        Displayable space = null;
        char spaceChar;
        for (int x = 0; x < objectGrid.length; x++) {
            for (int y = 0; y < objectGrid[0].length; y++) {
                space = getObjectFromDisplay(x, y);
                spaceChar = space.getChar();
                if (spaceChar == '.' || spaceChar == '#' || spaceChar == '+') {
                    spaces.add(space);
                }
            }
        }
        return spaces.get(random.nextInt(spaces.size()));
    }
}
