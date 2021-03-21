package parsers;

import java.util.ArrayList;
import java.util.List;

public class PlotParser implements ParserStrategy {

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
        } else if (line.contains("PL:")) {
            String PLregex = "PL:(.)"; // regex for splitting review
            if (line.split(PLregex).length > 1) {
                String rating = line.split(PLregex)[1];
                if (!checkingRating) {
                    checkingRating = true;
                    return currentMovie + "\t" + rating;
                }
            }
        }


        return null;
    }
}
