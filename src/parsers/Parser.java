package parsers;

import java.util.List;

public class Parser {

    protected ParserStrategy parserStrategy;
    protected String fileName;

    public Parser(String fileName, ParserStrategy parserStrategy) {
        this.fileName = fileName;
        this.parserStrategy = parserStrategy;
    }

    /**
     * Parse data with the currently held parsing strategy
     * @param data
     * @return
     */
    public List<String> parse(List<String> data) {
        return parserStrategy.parse(data);
    }


    /**
     * Get the file name that belongs to this parser
     * @return The file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Set the new parser strategy
     * @param strategy The new parser strategy to use
     */
    public void setParserStrategy(ParserStrategy strategy) {
        this.parserStrategy = strategy;
    }

}
