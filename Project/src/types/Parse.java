package types;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class Parse {
    public static DungeonXMLHandler parseXML(String fileName) {

        // Create a saxParserFactory
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        DungeonXMLHandler handler = new DungeonXMLHandler();

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
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
            handler = null;
        }
        return handler;
    }
}
