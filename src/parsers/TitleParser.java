package parsers;


import file_utils.DataFile;
import file_utils.SaveFile;
import program.ParserProgram;

import java.util.*;

public class TitleParser implements WholeFileParserStrategy {

    List<String> countries;
    ArrayList<String> MPAA;
    ArrayList<String> costs;
    ArrayList<String> plot;


    public TitleParser() {
        countries = new ArrayList<>();
        MPAA = new ArrayList<>();
        costs = new ArrayList<>();
        plot = new ArrayList<>();
        ParserProgram.parseFile("countries.list", countries, new CountriesParser());
        ParserProgram.parseFile("mpaa-ratings-reasons.list", MPAA, new MPAAParser());
        ParserProgram.parseFile("business.list", costs, new CostsParser());
        ParserProgram.parseFile("plot.list", plot, new PlotParser());
    }

    @Override
    public void parse(DataFile inputFile, SaveFile saveFile) {
        List<String> data = inputFile.readAll();
        data.set(0, data.get(0).replace("tconst", "titleID")); // replace tconst with titleID
        List<String> filteredData = getListWithoutGenre(getListWithoutSeries(data)); // remove genre and series from data
        filteredData.set(0, filteredData.get(0) + "region\tMPAA\tcost\tplot"); // add columns
        String tableHeading = filteredData.get(0);

        filteredData = mergeTitlesWithData(getTitleHashmap(filteredData), countries, tableHeading); // add countries to filtered data
       filteredData = mergeTitlesWithData(getTitleHashmap(filteredData), MPAA, tableHeading); // add MPAA to filtered data
        filteredData = mergeTitlesWithData(getTitleHashmap(filteredData), costs, tableHeading); // add budget to filtered data
        filteredData = mergeTitlesWithData(getTitleHashmap(filteredData), plot, tableHeading); // add plot to filtered data

        filteredData.replaceAll(line -> {
            String[] items = line.split("\t"); // Splits per tab

            for (int i = 0; i < items.length; i++) {
                if (items[i].contains(";")) {
                    items[i] = "\"" + items[i].replace("\"", "").replace("'", "").replace(";", "")+ "\"";
                }
            }
            return String.join((";"), items);
        });
        saveFile.addText((ArrayList<String>) filteredData);
        saveFile.save(true);

    }

    /**
     * remove all genres from list
     *
     * @param data list with movie title information
     * @return the same list without genres
     */
    private List<String> getListWithoutGenre(List<String> data) {
        data.replaceAll(line -> {
            String[] items = line.split("\t");
            items[items.length - 1] = ""; // remove the last element from the line (genre)

            return String.join("\t", items);
        });
        return data;
    }

    /**
     * remove all series from list
     *
     * @param data list with movie title information
     * @return a list without series
     */
    private List<String> getListWithoutSeries(List<String> data) {
        List<String> result = new ArrayList<>();
        for (String str : data) {
            if (!(str.contains("tvSeries") || str.contains("tvEpisode") || str.contains("tvMiniSeries") || str.contains("short")))
                result.add(str);
        }
        return result;
    }

    /**
     * merge given data with tilte data
     *
     * @param titles       hashmap with titles and data
     * @param data         list with title and country
     * @param tableHeading heading for the data (id, title, etc..)
     * @return a list with given data added to the title data
     */
    private List<String> mergeTitlesWithData(HashMap<String, String> titles, List<String> data, String tableHeading) {
        HashSet<String> checkedTitles = new HashSet<>();
        int lineCount = 0;
        for (String line : data) {
            if (lineCount > 14) { // data starts at line 15
                String name = line.split("\t")[0];
                String value = line.split("\t")[1];

                if (titles.containsKey(name)) {
                    if (checkedTitles.contains(name))
                        continue;
                    else {
                        checkedTitles.add(name);
                        String oldData = titles.get(name);
                        String newData = oldData + value + "\t";

                        titles.put(name, newData);
                    }
                }
            }
            if (lineCount <= 14)
                lineCount++;
        }

        titles.forEach((index, value) -> {
            if (!checkedTitles.contains(index)) {
                checkedTitles.add(index);
                String newData = value + "\\N\t";
                titles.put(index, newData);
            }
        });

        ArrayList<String> result = new ArrayList<>(titles.values());
        result.add(tableHeading);
        Collections.sort(result);
        return result;
    }

    /**
     * Create a hashmap from list
     *
     * @param data = list to put in hashmap
     * @return a hashmap with movie title as key and the data as value
     */
    private HashMap<String, String> getTitleHashmap(List<String> data) {
        HashMap<String, String> result = new HashMap<>();
        int lineCount = 0;
        for (String line : data) {
            if (lineCount > 0) {
                String name = line.split("\t")[3];
                result.put(name, line);
            }
            if (lineCount < 1)
                lineCount++;
        }
        return result;
    }


}
