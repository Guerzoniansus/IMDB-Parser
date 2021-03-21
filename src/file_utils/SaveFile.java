package file_utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A (CSV) save file for saving text.
 */
public class SaveFile {

    private final String filePath = "output/";
    private final String fileName;

    FileWriter writer;

    /**
     * Creates or opens a new text file
     * @param fileName
     */
    public SaveFile(String fileName) {
        this.fileName = fileName;

        try {
            writer = new FileWriter(filePath + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a line of text to this save file
     * @param line The line of text to append
     */
    public void addLine(String line) {
        try {
            writer.append(line);
            writer.append("\n");
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add multiple lines of text to this save file
     * @param text The multiple lines of text to append
     */
    public void addText(ArrayList<String> text) {

        try {
            for (String line : text) {
                writer.append(line);
                writer.append("\n");
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the file and saves it
     */
    public void save() {
        try {
            writer.flush();
            writer.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
