package parsers;

import file_utils.FileLoader;
import file_utils.LoaderListStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleParser implements ParserStrategy {


    @Override
    public List<String> parse(List<String> data) {
        List<String> CountryFile = FileLoader.getInstance().loadFile("countries.list", new LoaderListStrategy());
        List<String> MoviesAndCountries = getMovieAndCountry(CountryFile);


        data.set(0, data.get(0).replace("tconst", "titleID")); // replace tconst with titleID

        List<String> filteredData;
        filteredData = removeGenre(removeSeries(data)); // remove genre and series from data
        filteredData.set(0, filteredData.get(0) + "country");


        filteredData.replaceAll(line -> {
            String[] items = line.split("\t"); // Splits per tab

//            for (String movie : MoviesAndCountries) {
//                if (movie.split("\t")[0].equals(items[2]))
//                    items[items.length-1] += movie.split("\t")[1];
//            }

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
}
