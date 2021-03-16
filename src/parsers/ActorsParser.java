package parsers;

import java.util.List;

public class ActorsParser implements ParserStrategy {
    @Override
    public List<String> parse(List<String> data) {

        //if data[i] does not contain actor or actress remove line from data.


        data.replaceAll(line -> {
            String[] items = line.split("\t"); // Splits per tab

            for (int i = 0; i < items.length; i++) {
                if (items[i].contains(",")) {
                    items[i] = "\"" + items[i] + "\"";
                }

            }

            return String.join(",", items);
        });

        return data;
    }
}
