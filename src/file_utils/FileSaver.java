package file_utils;

import java.util.List;

public class FileSaver {


    private static FileSaver instance = new FileSaver();

    private FileSaver() {};

    /**
     * Get the singleton instance of file saver
     * @return The file saver instance
     */
    public static FileSaver getInstance() {
        return instance;
    }

    /**
     *
     * @param fileName
     * @param data
     * @param saverStrategy
     */
    public void saveFile(String fileName, List<String> data, SaverStrategy saverStrategy) {
        saverStrategy.saveFile(fileName, data);
    }
}
