package parsers;

import program.ParserProgram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingsParser implements ParserStrategy {

    private final int ID_COL_INDEX = 0;
    private final int RATING_COL_INDEX = 1;

    private boolean checkedFirstLine;

    private Map<String, String> IDandRatings;


    public RatingsParser () {
        checkedFirstLine = false;
        IDandRatings = new HashMap<>();

        ParserProgram.parseFile("title_ratings.tsv", new ArrayList<>(), new ParserStrategy() {
            @Override
            public String parse(String line) {
                if (line.contains("titleID"))
                    return null;

                String[] split = line.split("\t");
                IDandRatings.put(split[ID_COL_INDEX], split[RATING_COL_INDEX]);
                return null;
            }
        });
    }

    @Override
    public String parse(String line) {

        if (checkedFirstLine == false) {
            checkedFirstLine = true;
            line = line + ",rating";
            return line;
        }

        String ID = line.split(",")[ID_COL_INDEX];

        if (IDandRatings.containsKey(ID)) {
            return line + "," + IDandRatings.get(ID);
        }

        else {
            return null;
        }
    }
}