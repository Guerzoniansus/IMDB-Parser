package file_utils;

import java.util.ArrayList;
import java.util.List;

public class FileLoader {

    private static FileLoader instance = new FileLoader();

    private FileLoader() {};

    /**
     * Get the singleton instance of file loader
     * @return The file loader instance
     */
    public static FileLoader getInstance() {
        return instance;
    }

    /**
     * Attempt to load a file and return a list with every line
     * @param fileName The file path
     * @param loaderStrategy The strategy used for loading this file
     * @return
     */
    public List<String> loadFile(String fileName, LoaderStrategy loaderStrategy) {
        return loaderStrategy.loadFile(fileName);
    }

}
