package file_utils;

import java.util.List;

public interface SaverStrategy {

    /**
     * Attempt to save a file
     * @param fileName The file destination name
     * @param data The data to save, where each String in the list is a new line
     */
    void saveFile(String fileName, List<String> data);
}
