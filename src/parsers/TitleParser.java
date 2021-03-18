package parsers;

import java.util.ArrayList;
import java.util.List;

public class TitleParser implements ParserStrategy {


    @Override
    public List<String> parse(List<String> data) {
        List<String> filteredData;
        data.set(0, data.get(0).replace("tconst", "titleID")); // replace tconst with titleID

        filteredData = removeGenre(removeSeries(data)); // remove genre and series from data


        filteredData.replaceAll(line -> {
            String[] items = line.split("\t"); // Splits per tab

            return String.join(",", items);
        });

        return filteredData;

    }

    private List<String> removeGenre(List<String> data) {
        data.replaceAll(line -> {
            String[] items = line.split("\t");
            items[items.length - 1] = "";

            return String.join("\t", items);
        });
        return data;
    }

    private List<String> removeSeries(List<String> data) {
        List<String> result = new ArrayList<>();
        for (String str : data) {
            if (!(str.contains("tvSeries") || str.contains("tvEpisode") || str.contains("tvMiniSeries")))
                result.add(str);
        }
        return result;
    }
}
