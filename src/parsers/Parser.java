package parsers;

public class Parser {

    protected ParserStrategy parserStrategy;

    /**
     * Creates a Parser object
     * @param parserStrategy The parser strategy to use
     */
    public Parser(ParserStrategy parserStrategy) {
        this.parserStrategy = parserStrategy;
    }

    /**
     * Parse a line of data with the currently held parsing strategy
     * @param line The line to parse
     * @return The parsed line, or null or if the line should be filtered out and not included in the output
     */
    public String parse(String line) {
        return parserStrategy.parse(line);
    }


}
