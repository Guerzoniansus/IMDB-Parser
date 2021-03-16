package file_utils;

import java.util.ArrayList;
import java.util.List;

public interface LoaderStrategy {

    /**
     * Attempt to load a file
     * @param fileName Thes file name
     * @return The file where each line of text is a String in the list
     */
    List<String> loadFile(String fileName);

}
