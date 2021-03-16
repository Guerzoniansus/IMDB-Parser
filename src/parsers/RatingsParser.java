package parsers;

import java.util.ArrayList;
import java.util.List;

public class RatingsParser implements ParserStrategy {


    @Override
    public List<String> parse(List<String> data) {

        data.replaceAll(line -> {
            String[] items = line.split("\t"); // Splits per tab

            return String.join(",", items);
        });

        return data;
    }
}
