package parsers;

import java.util.List;

public interface OldParserStrategy {

    /**
     * Paresr the data
     * @param data The data to parse
     * @return The parsed data
     */
    List<String> parse(List<String> data);

}
