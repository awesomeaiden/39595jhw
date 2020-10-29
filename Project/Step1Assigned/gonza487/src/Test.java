import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class Test {
    public static void main(String[] args) {
        // Ensure file name passed in
        String fileName = null;
        if (args.length == 1) {
            fileName = "xmlFiles/" + args[0];
        } else {
            System.out.println("java Test <xmlfilename>");
            return;
        }

        // Create a saxParserFactory
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            DungeonXMLHandler handler = new DungeonXMLHandler();
            saxParser.parse(new File(fileName), handler);
            // This will change depending on what kind of XML we are parsing
            Dungeon dungeon = handler.getDungeon();
            ObjectDisplayGrid objdispgrid = handler.getObjectDisplayGrid();
            // print out all of the students.  This will change depending on
            // what kind of XML we are parsing
            System.out.println(dungeon);
            System.out.println(objdispgrid);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
