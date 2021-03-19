package parsers;

import file_utils.FileLoader;
import file_utils.LoaderListStrategy;

import java.util.*;

public class TitleParser implements ParserStrategy {


    @Override
    public List<String> parse(List<String> data) {
        List<String> countryFile = FileLoader.getInstance().loadFile("countries.list", new LoaderListStrategy());
        List<String> MPAAFile = FileLoader.getInstance().loadFile("mpaa-ratings-reasons.list", new LoaderListStrategy());
        List<String> moviesAndCountries = getMovieAndCountry(countryFile);

        data.set(0, data.get(0).replace("tconst", "titleID")); // replace tconst with titleID

        List<String> filteredData;
        filteredData = getListWithoutGenre(getListWithoutSeries(data)); // remove genre and series from data

        filteredData.set(0, filteredData.get(0) + "country\tMPAA");

        String tableHeading = filteredData.get(0);
        filteredData = mergeTitlesWithData(getTitleHashmap(filteredData), moviesAndCountries, tableHeading); // add countries to filtered data

        filteredData = mergeTitlesWithData(getTitleHashmap(filteredData), getMPAA(MPAAFile), tableHeading); // add MPAA to filtered data

        filteredData.replaceAll(line -> {
            String[] items = line.split("\t"); // Splits per tab

            for (int i = 0; i < items.length; i++) {
                if (items[i].contains(",")) {
                    items[i] = "\"" + items[i] + "\"";
                }

            }
            return String.join((","), items);
        });

        return filteredData;
    }

    /**
     * create a list of strings containing title and MPAA rating
     *
     * @param data list disorganized data
     * @return a organised list with title and MPAA rating per line
     */
    private List<String> getMPAA(List<String> data) {
        List<String> MPAAList = new ArrayList<>();

        String MVregex = "MV:(.)"; // regex for splitting movie title
        String REregex = "RE:(.)"; // regex for splitting review
        String regexParantheses = " \\W\\S"; // regex for removing (year)

        StringBuilder newLine = new StringBuilder();

        for (String line : data) {
            String[] items;
            if (line.contains("MV:")) {
                if (!newLine.isEmpty())
                    MPAAList.add(newLine.toString());
                items = line.split(MVregex); // Splits per tab
                String title = items[1].split(regexParantheses)[0];
                newLine = new StringBuilder(title + "\t");
            }
            if (line.contains("RE:")) {
                String rating = line.split(REregex)[1];
                newLine.append(rating);
            }
        }
        return MPAAList;
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
            if (!(str.contains("tvSeries") || str.contains("tvEpisode") || str.contains("tvMiniSeries")))
                result.add(str);
        }
        return result;
    }

    /**
     * @param data = countries.list
     * @return a list with: moviename and region
     */
    private List<String> getMovieAndCountry(List<String> data) {
        List<String> result = new ArrayList<>();
        final String regex = " \\W\\S";
        for (String movie : data) {
            String movieName = movie.split(regex)[0];
            String[] region = movie.split("\t");

            result.add(movieName + "\t" + region[region.length - 1]);
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
                        String newData = oldData + value+"\t";
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
