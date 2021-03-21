package parsers;

public class CostsParser implements ParserStrategy {

    String currentMovie;
    boolean checkingRating;

    @Override
    public String parse(String line) {

        String[] items;

        if (line.contains("MV:")) {
            String MVregex = "MV:(.)"; // regex for splitting movie title
            String regexParentheses = " \\W\\S"; // regex for removing (year)
            items = line.split(MVregex); // Split string on mv
            String title = items[1].split(regexParentheses)[0]; // get string between "MV: " and  "("
            currentMovie = title + "\t";
            checkingRating = false;
            return null;
        }

        if (line.contains("BT:")) {
            String REregex = "BT:(.)"; // regex for splitting review
            String budget = line.split(REregex)[1]; // get string after BT:

            String currency = budget.split(" ")[0]; // get currency (EUR, USD, GBP,...)
            String amount = budget.split(" ")[1];
            if (!checkingRating) {
                checkingRating = true;
                return currentMovie + convertCurrencyToUSD(currency, amount.replace(",", ""));
            }
        }
        return null;
    }

    /**
     * Convert currency to USD
     * @param currency The currency
     * @param amount The amount of money
     * @return amount in USD
     */
    private String convertCurrencyToUSD(String currency, String amount) {

        String newCurrency;

        switch (currency) {
            case "USD":
                newCurrency = amount;
                break;
            case "AUD":
                newCurrency = String.valueOf(Double.parseDouble(amount) * 0.77);
                break;
            case "EUR":
                newCurrency = String.valueOf(Double.parseDouble(amount) * 1.90);
                break;
            case "CAD":
                newCurrency = String.valueOf(Double.parseDouble(amount) * 0.80);
                break;
            case "INR":
                newCurrency = String.valueOf(Double.parseDouble(amount) * 0.014);
                break;
            case "GBP":
                newCurrency = String.valueOf(Double.parseDouble(amount) * 1.39);
                break;
            default:
                newCurrency = "\\N";
        }

        return newCurrency;
    }
}
