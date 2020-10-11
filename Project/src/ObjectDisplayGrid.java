public class ObjectDisplayGrid {
    // ObjectDisplayGrid private attributes
    private int gameHeight;
    private int width;
    private int topHeight;
    private int bottomHeight;
    private static ObjectDisplayGrid odg = null;

    private ObjectDisplayGrid(int _gameHeight, int _width, int _topHeight, int _bottomHeight) {
        System.out.println("Creating an ObjectDisplayGrid: " + _gameHeight + _width + _topHeight + _bottomHeight);
        gameHeight = _gameHeight;
        width = _width;
        topHeight = _topHeight;
        bottomHeight = _bottomHeight;
    }

    // Used by code outside the class to get a dungeon
    public ObjectDisplayGrid getObjectDisplayGrid(int gameHeight, int width, int topHeight, int bottomHeight) throws Exception {
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
}
