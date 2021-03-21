package parsers;

public interface ParserStrategy {

    /**
     * Parses a line of data
     * @param line The data to parse
     * @return The parsed line, or null if the line should be filtered out instead of included in the output
     */
    String parse(String line);

}
