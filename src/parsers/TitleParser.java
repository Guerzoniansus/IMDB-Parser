package parsers;

import file_utils.FileLoader;
import file_utils.LoaderListStrategy;

import java.util.*;

public class TitleParser implements ParserStrategy {


    @Override
    public List<String> parse(List<String> data) {
        data.set(0, data.get(0).replace("tconst", "titleID")); // replace tconst with titleID
        List<String> filteredData = getListWithoutGenre(getListWithoutSeries(data)); // remove genre and series from data
        filteredData.set(0, filteredData.get(0) + "country\tMPAA\tcost\tplot"); // add columns

        String tableHeading = filteredData.get(0);

        List<String> countryFile = FileLoader.getInstance().loadFile("countries.list", new LoaderListStrategy());
        filteredData = mergeTitlesWithData(getTitleHashmap(filteredData), getMovieAndCountry(countryFile), tableHeading); // add countries to filtered data


        List<String> MPAAFile = FileLoader.getInstance().loadFile("mpaa-ratings-reasons.list", new LoaderListStrategy());
        filteredData = mergeTitlesWithData(getTitleHashmap(filteredData), getMPAA(MPAAFile), tableHeading); // add MPAA to filtered data


        List<String> businessFile = FileLoader.getInstance().loadFile("business.list", new LoaderListStrategy());
        filteredData = mergeTitlesWithData(getTitleHashmap(filteredData), getBudget(businessFile), tableHeading); // add budget to filtered data

//        List<String> plotFile = FileLoader.getInstance().loadFile("plot.list", new LoaderListStrategy());

//        filteredData = mergeTitlesWithData(getTitleHashmap(filteredData), getPlot(plotFile), tableHeading); // add budget to filtered data


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
        String regexParentheses = " \\W\\S"; // regex for removing (year)

        StringBuilder newLine = new StringBuilder();

        for (String line : data) {
            String[] items;
            if (line.contains("MV:")) {
                if (!newLine.isEmpty())
                    MPAAList.add(newLine.toString());
                items = line.split(MVregex); // Splits per tab
                String title = items[1].split(regexParentheses)[0];
                newLine = new StringBuilder(title + "\t");
            }
            if (line.contains("RE:")) {
                String rating = line.split(REregex)[1];
                newLine.append(rating);
            }
        }
        return MPAAList;
    }

    private List<String> getPlot(List<String> data) {

        List<String> plotList = new ArrayList<>();

        String MVregex = "MV:(.)"; // regex for splitting movie title
        String PLregex = "PL:(.)"; // regex for splitting review
        String regexParentheses = " \\W\\S"; // regex for removing (year)

        StringBuilder newLine = new StringBuilder();

        for (String line : data) {
            String[] items;
            if (line.contains("MV:")) {
                if (!newLine.isEmpty())
                    plotList.add(newLine.toString());
                items = line.split(MVregex); // Splits per tab
                String title = items[1].split(regexParentheses)[0];
                newLine = new StringBuilder(title + "\t");
            }
            if (line.contains("PL: ")) {
                if (line.split(PLregex).length > 1) {
                    String plot = line.split(PLregex)[1];
                    newLine.append(plot);
                }
            }
        }
        return plotList;
    }

    /**
     * create a list of strings containing title and MPAA rating
     *
     * @param data list disorganized data
     * @return a organised list with title and MPAA rating per line
     */
    private List<String> getBudget(List<String> data) {
        List<String> budgetList = new ArrayList<>();

        String MVregex = "MV:(.)"; // regex for splitting movie title
        String REregex = "BT:(.)"; // regex for splitting review
        String regexParentheses = " \\W\\S"; // regex for removing (year)

        StringBuilder newLine = new StringBuilder();

        for (String line : data) {
            String[] items;

            if (line.contains("MV:")) {
                if (!newLine.isEmpty())
                    budgetList.add(newLine.toString());

                items = line.split(MVregex); // Split string on mv
                String title = items[1].split(regexParentheses)[0]; // get string between "MV: " and  "("
                newLine = new StringBuilder(title + "\t");
            }

            if (line.contains("BT:")) {
                String budget = line.split(REregex)[1]; // get string after BT:
                newLine.append(budget);
            }
        }

        List<String> result = new ArrayList<>();
        for (String line : budgetList) {
            String[] items = line.split("\t"); // Splits per tab
            if (movieHasBudget(items)) {
                String currency = items[1].split(" ")[0]; // get currency (EUR, USD, GBP,...)
                String amount = items[1].split(" ")[1];

                result.add(items[0] + "\t" + convertCurrencyToUSD(currency, amount.replace(",", "")));
            }
        }
        return result;
    }

    private Boolean movieHasBudget(String[] data) {
        return (data.length > 1);
    }

    private String convertCurrencyToUSD(String currency, String amount) {
        return switch (currency) {
            case "USD" -> amount;
            case "AUD" -> String.valueOf(Double.parseDouble(amount) * 0.77);
            case "EUR" -> String.valueOf(Double.parseDouble(amount) * 1.90);
            case "CAD" -> String.valueOf(Double.parseDouble(amount) * 0.80);
            case "INR" -> String.valueOf(Double.parseDouble(amount) * 0.014);
            case "GBP" -> String.valueOf(Double.parseDouble(amount) * 1.39);
            default -> "\\n";
        };
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

    private List<String> getAmountOfValues(List<String> data) {

        List<String> result = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();
        for (String line : data) {
            String[] item1 = line.split("\t");
            String[] item = item1[item1.length - 1].split(" ");
            if (map.containsKey(item[0]))
                map.put(item[0], map.get(item[0]) + 1);
            else
                map.put(item[0], 1);
        }
        map.forEach((index, value) -> {
            result.add(index + "," + value);
        });

        return result;
    }

}
