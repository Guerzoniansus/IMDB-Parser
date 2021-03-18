package parsers;

import java.util.List;

public class TitleParser implements ParserStrategy {


    @Override
    public List<String> parse(List<String> data) {

        data.set(0, data.get(0).replace("tconst", "titleID")); // replace tconst with titleID

        List<String> newData = removeGenre(data);

        newData.replaceAll(line -> {
            String[] items = line.split("\t"); // Splits per tab

            return String.join(",", items);
        });

        return newData;

    }

    private List<String> removeGenre(List<String> data) {
        data.replaceAll(line -> {
            String[] items = line.split("\t");
            items[items.length - 1] = "";

            return String.join("\t", items);
        });
        return data;
    }
}
