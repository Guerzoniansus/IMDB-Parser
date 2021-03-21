package file_utils;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Class that contains a BufferedReader to read a text file
 * but encapsulates the object itself with a simple interface
 */
public class DataFile {

    private final String filePath = "input-files/";
    private final String fileName;

    BufferedReader reader;

    /**
     * Tries to open a new text file
     * @param fileName The file name of the text file to open
     */
    public DataFile(String fileName) {

        this.fileName = fileName;

        try {
            reader = new BufferedReader(new FileReader(filePath + fileName));
        }

        catch (Exception e) {
            System.out.println("Something went wrong trying to load " + fileName);
            System.out.println(e);
        }
    }

    /**
     * Read the next line from the text file
     * @return The next line, or null if there are no lines left
     */
    public String readLine() {
        try {
            return reader.readLine();
        }

        catch (Exception e) {
            System.out.println("Could not read from " + fileName);
            System.out.println(e);
        }

        return null;
    }

    /**
     * Get this data file's file name
     * @return The file name
     */
    public String getFileName() {
        return fileName;
    }


}
