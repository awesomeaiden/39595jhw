package game;

import types.*;
import java.util.ArrayList;

public class Rogue implements Runnable {

    private static final int DEBUG = 0;
    private int playerPosX;
    private int playerPosY;
    private boolean isRunning;
    public static final int FRAMESPERSECOND = 60;
    public static final int TIMEPERLOOP = 1000000000 / FRAMESPERSECOND;
    private static types.ObjectDisplayGrid displayGrid = null;
    private static types.Dungeon dungeon = null;
    private Thread keyStrokePrinter;
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;

    private Thread PlayerMover;
    private static Player player;

    @Override
    public void run() {
        try { // wait for asciipanel initialization
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // displayGrid.fireUp();
        int line;
        int col;
        int roomInd;
        Room room;
        int playerHP = 0;
        int playerCore = 0;
        ArrayList<Room> rooms = dungeon.getRooms();
        for (roomInd = 0; roomInd < rooms.size(); roomInd++) {
            // First draw borders
            room = rooms.get(roomInd);
            for (line = room.getPosY() + displayGrid.getTopHeight(); line < room.getPosY() + room.getHeight() + displayGrid.getTopHeight(); line++) {
                if (line == room.getPosY() + displayGrid.getTopHeight() || line == room.getPosY() + room.getHeight() + displayGrid.getTopHeight() - 1) { // top or bottom
                    for (col = room.getPosX(); col < room.getPosX() + room.getWidth(); col++) {
                        displayGrid.addObjectToDisplay(new Char('X'), col, line);
                    }
                } else { // middle
                    displayGrid.addObjectToDisplay(new Char('X'), room.getPosX(), line);
                    for (col = room.getPosX() + 1; col < room.getPosX() + room.getWidth() - 1; col++) {
                        displayGrid.addObjectToDisplay(new Char('.'), col, line);
                    }
                    displayGrid.addObjectToDisplay(new Char('X'), room.getPosX() + room.getWidth() - 1, line);
                }
            }
            // Now add creatures
            ArrayList<Creature> creatures = room.getCreatures();
            Creature creature;
            int creatureInd;
            for (creatureInd = 0; creatureInd < creatures.size(); creatureInd++) {
                creature = creatures.get(creatureInd);
                if (creature instanceof Player) {
                    playerHP = creature.getHp();
                    playerPosX = creature.getPosX() + room.getPosX();
                    playerPosY = creature.getPosY() + room.getPosY() + displayGrid.getTopHeight();
                    displayGrid.addObjectToDisplay(new Char('.'), room.getPosX() + creature.getPosX(),
                            room.getPosY() + creature.getPosY() + displayGrid.getTopHeight());
                    player = (Player)creature;
                }
                displayGrid.addObjectToDisplay(creature, room.getPosX() + creature.getPosX(),
                        room.getPosY() + creature.getPosY() + displayGrid.getTopHeight());
            }
            // Now add items
            ArrayList<Item> items = room.getItems();
            Item item;
            int itemInd;
            for (itemInd = 0; itemInd < items.size(); itemInd++) {
                item = items.get(itemInd);
                displayGrid.addObjectToDisplay(item, room.getPosX() + item.getPosX(),
                        room.getPosY() + item.getPosY() + displayGrid.getTopHeight());
            }
        }

        // Now draw passages:
        ArrayList<Passage> passages = dungeon.getPassages();
        Passage passage;
        int passageInd;
        for (passageInd = 0; passageInd < passages.size(); passageInd++) {
            passage = passages.get(passageInd);
            drawPassage(passage);
        }

        // Now draw top area
        String topText = "HP: " + playerHP + "  core:  " + playerCore;
        for (col = 0; col < topText.length(); col++) {
            displayGrid.addObjectToDisplay(new Char(topText.charAt(col)), col, 0);
        }

        // Now draw bottom area
        String packString = "Pack:";
        for (col = 0; col < packString.length(); col++) {
            displayGrid.addObjectToDisplay(new Char(packString.charAt(col)), col, displayGrid.getHeight() - 3);
        }
        String infoString = "Info:";
        for (col = 0; col < infoString.length(); col++) {
            displayGrid.addObjectToDisplay(new Char(infoString.charAt(col)), col, displayGrid.getHeight() - 1);
        }
    }

    public void drawPassage(Passage passage) {
        int posInd;
        ArrayList<Integer> posXs = passage.getPosXs();
        ArrayList<Integer> posYs = passage.getPosYs();
        Integer posX1;
        Integer posY1;
        Integer posX2;
        Integer posY2;
        for (posInd = 0; posInd < posXs.size() - 1; posInd++) {
            posX1 = posXs.get(posInd);
            posY1 = posYs.get(posInd);
            posX2 = posXs.get(posInd + 1);
            posY2 = posYs.get(posInd + 1);
            if (posX1.equals(posX2)) { // vertical passage
                drawVerticalLine(posX1, posY1, posY2);
            } else if (posY1.equals(posY2)) { // horizontal passage
                drawHorizontalLine(posX1, posY1, posX2);
            }
        }
    }

    public void drawVerticalLine(Integer posX1, Integer posY1, Integer posY2) {
        while (!posY1.equals(posY2)) {
            addPassageChar(posX1, posY1);
            if (posY1 < posY2) {
                posY1++;
            } else {
                posY1--;
            }
        }
        addPassageChar(posX1, posY1);
    }

    public void drawHorizontalLine(Integer posX1, Integer posY1, Integer posX2) {
        while (!posX1.equals(posX2)) {
            addPassageChar(posX1, posY1);
            if (posX1 < posX2) {
                posX1++;
            } else {
                posX1--;
            }
        }
        addPassageChar(posX1, posY1);
    }

    public void addPassageChar(Integer posX, Integer posY) {
        Object curObj = displayGrid.getObjectFromDisplay(posX, posY + displayGrid.getTopHeight());
        if (curObj instanceof Char) {
            if (((Char)curObj).getChar() == 'X') {
                displayGrid.addObjectToDisplay(new Char('+'), posX, posY + displayGrid.getTopHeight());
            } else {
                displayGrid.addObjectToDisplay(new Char('#'), posX, posY + displayGrid.getTopHeight());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Ensure file name passed in
        String filename = null;
        if (args.length == 1) {
            filename = "xmlFiles/" + args[0];
        } else {
            System.out.println("java Test <xmlfilename>");
            return;
        }

        // Parse dungeon xml file
        DungeonXMLHandler handler = Parse.parseXML(filename);
        if (handler == null) {
            return;
        }

        // Get results
        dungeon = handler.getDungeon();
        displayGrid = handler.getObjectDisplayGrid();
        if (dungeon == null || displayGrid == null) {
            return;
        }

        // Draw dungeon
        Rogue rogue = new Rogue();
        Thread rogueThread = new Thread(rogue);
        rogueThread.start();

        rogueThread.join();
        rogue.PlayerMover = new Thread(new PlayerMover(displayGrid, rogue.playerPosX, rogue.playerPosY, player));
        System.out.println(dungeon);
        rogue.PlayerMover.start();
        rogue.PlayerMover.join();
    }
}
