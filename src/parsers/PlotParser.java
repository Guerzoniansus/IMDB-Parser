package parsers;

import java.util.ArrayList;
import java.util.List;

public class PlotParser implements ParserStrategy {
    String currentMovie;
    String currentPlot;

    boolean checkingPlot;

    public PlotParser() {
        currentMovie = null;
        currentPlot = "";

        checkingPlot = false;
    }


    @Override
    public String parse(String line) {

        if (line.contains("MV:")) {

            checkingPlot = false;

            String MVregex = "MV:(.)"; // regex for splitting movie title
            String regexParentheses = " \\W\\S"; // regex for removing (year)
            String[] items = line.split(MVregex); // Splits per tab

            currentMovie = items[1].split(regexParentheses)[0];
            currentPlot = "";


            return null;
        }

        else if (line.contains("PL:")) {

            checkingPlot = true;

            String PLregex = "PL:(.)"; // regex for splitting review


            if (line.split(PLregex).length > 1) {
                String plot = line.split(PLregex)[1];

                currentPlot += plot;
            }
        }
        else {
            if (checkingPlot) {
                checkingPlot = false;
                return currentMovie + "\t" + currentPlot;
            }

        }


        return null;
    }
}
