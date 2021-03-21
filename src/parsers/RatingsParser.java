package parsers;

import java.util.List;

public class RatingsParser implements ParserStrategy {

    @Override
    public String parse(String line) {
        line.replace("tconst", "titleID");
        return String.join(",", line.split("\t"));
    }
}