package parsers;

import java.util.List;

public class MoviesParser  {


    // Oud bestand? Geen idee waar dit voor is

    public List<String> parse(List<String> data) {
        data.replaceAll(line -> {
            String[] items = line.split("\t"); // Splits per tab

            for (int i = 0; i < items.length; i++) {
                if(items[i].contains(",")){
                    items[i] = "\"" + items[i] + "\"";
                }
            }

            return String.join(",", items);
        });

        return data;
    }
}
