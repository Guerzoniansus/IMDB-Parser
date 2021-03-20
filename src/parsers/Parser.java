package parsers;

import java.util.List;

public class Parser {

    protected ParserStrategy parserStrategy;
    protected String fileName;
    protected String outputFileName;

    public Parser(String fileName, ParserStrategy parserStrategy, String outputFileName) {
        this.fileName = fileName;
        this.parserStrategy = parserStrategy;
        this.outputFileName = outputFileName;
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

    /**
     * Get the output file name intended for this parser
     * @return File name to be used for the output file
     */
    public String getOutputFileName() { return outputFileName; }

}
