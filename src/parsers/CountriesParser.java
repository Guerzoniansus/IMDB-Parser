package parsers;

import java.util.ArrayList;
import java.util.List;

public class CountriesParser implements ParserStrategy {
    private int lineCount = 0;


    @Override
    public String parse(String line) {
        final String regex = " \\W\\S";
        String movieName = line.split(regex)[0];
        String[] region = line.split("\t");
        lineCount++;
        if(lineCount < 15)
            return null;

        return movieName + "\t" + region[region.length - 1];
    }
}
