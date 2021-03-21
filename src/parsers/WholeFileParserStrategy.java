package parsers;

import file_utils.DataFile;
import file_utils.SaveFile;

/**
 * A parser that loads and saves entire files at once instead of per line
 */
public interface WholeFileParserStrategy {

    /**
     * Parse a file. The parser needs to manually invoke saveFile.add() and saveFile.save() when done
     * @param inputFile
     * @param saveFile
     */
    void parse(DataFile inputFile, SaveFile saveFile);

}
