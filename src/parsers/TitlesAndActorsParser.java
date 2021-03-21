package parsers;

import javax.naming.NameParser;
import java.util.ArrayList;
import java.util.List;

public class TitlesAndActorsParser implements ParserStrategy {

    private final int PERSON_ID_COL_INDEX = 0;
    private final int TITLE_ID_COL_INDEX = 5;

    boolean checkFirstLine;

    public TitlesAndActorsParser() {
        checkFirstLine = false;
    }

    @Override
    public String parse(String line) {

        if (!checkFirstLine) {
            checkFirstLine = true;
            return replaceColumnNames();
        }

        if ((line.contains("actor") || line.contains("actress")) == false) {
            return null;
        }

        String[] items = line.split("\t");
        String PersonID = items[PERSON_ID_COL_INDEX];

        String[] FilmIDs = items[TITLE_ID_COL_INDEX].split(",");

        String returnString = "";


        for (String TitleID : FilmIDs) {
            returnString += PersonID + "," + TitleID + "\n";
        }

        return returnString;
    }

    private String replaceColumnNames() {
        return "actorID" + "," + "titleID";
    }
}