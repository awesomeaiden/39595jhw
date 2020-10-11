public class ObjectDisplayGrid {
    // ObjectDisplayGrid private attributes
    private int gameHeight;
    private int width;
    private int topHeight;
    private static ObjectDisplayGrid odg = null;

    private ObjectDisplayGrid(int _gameHeight, int _width, int _topHeight) {
        gameHeight = _gameHeight;
        width = _width;
        topHeight = _topHeight;
    }

    // Used by code outside the class to get a dungeon
    public ObjectDisplayGrid getObjectDisplayGrid(int gameHeight, int width, int topHeight) throws Exception {
        if (odg == null) { // create dungeon with name, width, and gameHeight
            odg = new ObjectDisplayGrid(gameHeight, width, topHeight);
            return odg;
        } else if (odg.isSame(gameHeight, width, topHeight)) {
            return odg;
        } else {
            throw new Exception("ObjectDisplayGrid specs don't match!");
        }
    }

    public boolean isSame(int _gameHeight, int _width, int _topHeight) {
        return (gameHeight == _gameHeight && width == _width && topHeight == _topHeight);
    }
}
