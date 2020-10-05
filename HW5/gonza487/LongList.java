public class LongList implements MyList {
    private LongList next;
    private long data;

    public LongList(LongList n, long _data) {
        next = n;
        data = _data;
    }

    public long getData() {
        return data;
    }

    public LongList next() {
        return next;
    }

    public void printNode() {
        System.out.println("LongList Node, data is: " + data);
    }
}
