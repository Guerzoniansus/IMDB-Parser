package parsers;

import java.util.ArrayList;
import java.util.List;

public class CountriesParser implements ParserStrategy {


    @Override
    public String parse(String line) {

        final String regex = " \\W\\S";
        String movieName = line.split(regex)[0];
        String country = line.split("\t")[line.split("\t").length - 1];


        return movieName + "\t" + country;
    }
}
