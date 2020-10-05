public class IntList implements MyList {
    private IntList next;
    private int data;

    // Prepend n with new node
    public IntList(IntList n, int _data) {
        next = n;
        data = _data;
    }

    public int getData() {
        return data;
    }

    public IntList next() {
        return next;
    }

    public void printNode() {
        System.out.println("IntList Node, data is: " + data);
    }
}
