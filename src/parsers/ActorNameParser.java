package parsers;

import program.ParserProgram;
import java.util.HashSet;

/**
 * Creates a file containing actor IDs and their names
 */
public class ActorNameParser implements ParserStrategy {

    private final int ID_COL_INDEX = 0;
    private final int NAME_COL_INDEX = 1;

    // List of IDs of people who are actors
    // Hash set because .contains() is O(1)
    private HashSet<String> actorIDs;

    private boolean checkedFirstLine;

    public ActorNameParser() {
        checkedFirstLine = false;
        actorIDs = new HashSet<>();

        // Get actor IDs
        ParserProgram.parseFile("title_principals.tsv", actorIDs, new ActorFilterParser());
    }

    @Override
    public String parse(String line) {

        if (checkedFirstLine == false) {
            checkedFirstLine = true;
            return replaceColumnNames();
        }

        String[] items = line.split("\t");

        String ID = items[ID_COL_INDEX];

        // Check if this person is an actor or not
        if (actorIDs.contains(ID) == false) {
            return null;
        }

        else {
            String name = items[NAME_COL_INDEX].replace(",", "");

            return ID + "," + name;
        }
    }


    /**
     * Replace the column names of the first line with new headers
     * @return The new column headers
     */
    private String replaceColumnNames() {
        return "personID,primaryName";
    }

}
