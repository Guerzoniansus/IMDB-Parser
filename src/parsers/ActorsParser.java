package parsers;

import java.util.ArrayList;
import java.util.List;

public class ActorsParser implements ParserStrategy {
    @Override
    public List<String> parse(List<String> data) {
        List<String> filteredData = new ArrayList<>();

        //if data[i] does not contain actor or actress remove line from data.
        for (String str : data) {
            if(str.contains("actor") || str.contains("actress"))
                filteredData.add(str);
        }

        filteredData.replaceAll(line -> {
            String[] items = line.split("\t"); // Splits per tab

            for (int i = 0; i < items.length; i++) {
                if (items[i].contains(",")) {
                    items[i] = "\"" + items[i] + "\"";
                }
            }

            return String.join(",", items);
        });

        return filteredData;
    }
}