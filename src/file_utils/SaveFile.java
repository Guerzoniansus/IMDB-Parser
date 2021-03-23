package file_utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

/**
 * A (CSV) save file for saving text.
 */
public class SaveFile {

    private final String inputFilePath = "input-files/";
    private final String outputFilePath = "output/";
    private final String fileName;

    FileWriter writer;

    /**
     * Creates or opens a (new) text file
     * @param fileName The file name of the output text file
     */
    public SaveFile(String fileName) {
        this.fileName = fileName;

        try {
            writer = new FileWriter(outputFilePath + fileName);

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

            if (line.endsWith("\n") == false) {
                writer.append("\n");
            }
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

                if (!line.endsWith("\n")) {
                    writer.append("\n");
                }
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the file and saves it
     * @param addCopyToInputDirectory Whether or not a copy of the saved file should be placed in the input directory
     */
    public void save(boolean addCopyToInputDirectory) {
        try {
            writer.flush();
            writer.close();

            if (addCopyToInputDirectory == true) {
                Files.copy(new File(outputFilePath + fileName).toPath(),
                        new File(inputFilePath + fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
