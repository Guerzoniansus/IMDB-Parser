package parsers;


/**
 * Parser used for testing
 */
public class TestParser implements ParserStrategy {

    @Override
    public String parse(String line) {
        return "one" + "\n" + "two";
    }
}
