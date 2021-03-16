package parsers;

import java.util.List;

public class TestParser implements ParserStrategy {


    @Override
    public List<String> parse(List<String> data) {

        data.replaceAll(line -> {
            String[] items = line.split("\t"); // Splits per tab

            for (int i = 0; i < items.length; i++) {
                items[i] = "X" + items[i];
            }

            return String.join(",", items);
        });

        return data;
    }
}
