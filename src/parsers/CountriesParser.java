package parsers;

import java.util.ArrayList;
import java.util.List;

public class CountriesParser implements ParserStrategy {


    @Override
    public String parse(String line) {

        List<String> result = new ArrayList<>();
        final String regex = " \\W\\S";
        String movieName = line.split(regex)[0];
        String[] region = line.split("\t");

        return movieName + "\t" + region[region.length - 1];

    }
}
