package parsers;

/**
 * Parser for making a table with each movie and genres
 */
public class GenresParser implements ParserStrategy {

    private final int ID_COL_INDEX = 0;
    private final int GENRE_COL_INDEX = 8;

    boolean checkedFirstLine;

    public GenresParser() {
        checkedFirstLine = false;
    }

    @Override
    public String parse(String line) {

        if (checkedFirstLine == false) {
            checkedFirstLine = true;
            return replaceColumnNames();
        }

        String[] data = line.split("\t");
        String ID = data[ID_COL_INDEX];

        String[] genres = data[GENRE_COL_INDEX].split(",");

        String returnString = "";

        for (String genre : genres) {
            returnString += ID + "," + genre + "\n";
        }

        return returnString;
    }

    private String replaceColumnNames() {
        return "titleID" + "," + "genre";
    }
}
