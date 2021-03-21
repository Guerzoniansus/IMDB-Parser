package parsers;

import java.util.ArrayList;
import java.util.List;

public class TitlesAndActorsParser implements ParserStrategy {


    @Override
    public String parse(String line) {

        // Opmerking voor Kevin:
        // Je returnt 1 String
        // Maar aangezien 1 acteur in meerdere films speelt,
        // moet je dus eigenlijk meerdere regels returnen.
        // Dat kun je zo doen:

        // return regel1 + "\n" + regel2 + "\n" + "regel3"
        // De \n maakt er dan een nieuwe regel van

        return null;
    }

    // Oude code van Kevin

    /*
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
     */
}
