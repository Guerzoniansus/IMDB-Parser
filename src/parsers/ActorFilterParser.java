package parsers;

/**
 * Filters out people, returning a list of people who are ONLY actors (so no directors or writers etc.).
 */
public class ActorFilterParser implements ParserStrategy {

    // Column index of ID in title_principals.tsv
    private final int ID_COL_INDEX = 2;

    @Override
    public String parse(String line) {

        String[] items = line.split("\t");

        if ((line.contains("actor") || line.contains("actress")) == false) {
            return null;

        }

        return items[ID_COL_INDEX];
    }

}
