import java.util.ArrayList;

public class Passage extends Structure {
    // Passage attributes
    private String name;
    private String id;

    public Passage() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int room1, int room2) {
        id = room1 + "-" + room2;
    }
}
