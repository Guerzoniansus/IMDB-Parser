package parsers;

import java.util.ArrayList;
import java.util.List;

public class MPAAParser implements ParserStrategy {
    String currentMovie;
    boolean checkingRating = false;

    @Override
    public String parse(String line) {
        if (line.contains("MV:")) {
            String MVregex = "MV:(.)"; // regex for splitting movie title
            String regexParentheses = " \\W\\S"; // regex for removing (year)
            String[] items = line.split(MVregex); // Splits per tab
            currentMovie = items[1].split(regexParentheses)[0];
            checkingRating = false;
            return null;
        } else if (line.contains("RE:")) {
            String REregex = "RE:(.)"; // regex for splitting review
            String rating = line.split(REregex)[1];
            if (!checkingRating) {
                checkingRating = true;
                return currentMovie + "\t" + rating;
            }
        }
        return null;
    }
}

/**
 regex "rated **"
 **/