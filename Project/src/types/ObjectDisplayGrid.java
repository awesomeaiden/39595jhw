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
    private Char[][] objectGrid = null;

    private List<InputObserver> inputObservers = null;

    private ObjectDisplayGrid(int _gameHeight, int _width, int _topHeight, int _bottomHeight) {
        System.out.println("Creating an ObjectDisplayGrid: " + _gameHeight + _width + _topHeight + _bottomHeight);
        gameHeight = _gameHeight;
        width = _width;
        topHeight = _topHeight;
        bottomHeight = _bottomHeight;
        setHeight();

        terminal = new AsciiPanel(width, height);
        objectGrid = new Char[width][height];

        initializeDisplay();

        super.add(terminal);
        super.setSize(width * 9, height * 16);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
        terminal.setVisible(true);
        super.addKeyListener(this);
        inputObservers = new ArrayList<>();
        super.repaint();
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
        for (InputObserver observer : inputObservers) {
            observer.observerUpdate(ch);
            if (DEBUG > 0) {
                System.out.println(CLASSID + ".notifyInputObserver " + ch);
            }
        }
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
        Char ch = new Char(' ');
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
//                addObjectToDisplay(ch, i, j);
                char testChar = (char) ((i + j) % 10 + 48);
                addObjectToDisplay(new Char(testChar), i, j);
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

    public void addObjectToDisplay(Char ch, int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                objectGrid[x][y] = ch;
                writeToTerminal(x, y);
            }
        }
    }

    private void writeToTerminal(int x, int y) {
        char ch = objectGrid[x][y].getChar();
        terminal.write(ch, x, y);
        terminal.repaint();
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
}
