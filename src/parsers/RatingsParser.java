package parsers;

import java.util.List;

public class RatingsParser implements OldParserStrategy {


    @Override
    public List<String> parse(List<String> data) {

        data.replaceAll(line -> {
            String[] items = line.split("\t"); // Splits per tab
            data.set(0, data.get(0).replace("tconst", "TitleID"));

            return String.join(",", items);
        });

        return data;
    }
}