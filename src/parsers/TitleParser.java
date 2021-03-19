package parsers;

import file_utils.FileLoader;
import file_utils.LoaderListStrategy;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleParser implements ParserStrategy {


    @Override
    public List<String> parse(List<String> data) {
        List<String> countryFile = FileLoader.getInstance().loadFile("countries.list", new LoaderListStrategy());
        List<String> moviesAndCountries = getMovieAndCountry(countryFile);

        data.set(0, data.get(0).replace("tconst", "titleID")); // replace tconst with titleID


        List<String> filteredData;
        filteredData = removeGenre(removeSeries(data)); // remove genre and series from data
        filteredData.set(0, filteredData.get(0) + "country");
        HashMap<String, String> titles = new HashMap<>();

        for (String line : filteredData) {
            String name = line.split("\t")[3];
            titles.put(name, line);
        }


        filteredData = addCountriesToList(titles, moviesAndCountries);


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

    private List<String> addCountriesToList(HashMap<String, String> titles, List<String> countries) {
        HashSet<String> checkedCountryTitles = new HashSet<>();
        int lineCount = 0;
        for (String line : countries) {
            if (lineCount > 14) { // data starts at line 15
                String name = line.split("\t")[0];
                String country = line.split("\t")[1];
                if (titles.containsKey(name)) {
                    if (checkedCountryTitles.contains(name))
                        continue;
                    else {
                        checkedCountryTitles.add(name);
                        String oldData = titles.get(name);
                        String newData = oldData + country;
                        titles.put(name, newData);
                    }
                }
            }
            if (lineCount <= 14)
                lineCount++;
        }

        titles.forEach((index, value) -> {
            if (!checkedCountryTitles.contains(index)) {
                checkedCountryTitles.add(index);
                String newData = value + "\\N";
                titles.put(index, newData);
            }
        });

        ArrayList<String> result = new ArrayList<>(titles.values());
        Collections.sort(result);
        return result;
    }
}
