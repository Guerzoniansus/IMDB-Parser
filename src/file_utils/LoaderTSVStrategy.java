package file_utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * This class is for loading .tsv files
 */
public class LoaderTSVStrategy implements LoaderStrategy {

    private final String filePath = "input-files/";

    @Override
    public ArrayList<String> loadFile(String fileName) {

        // ========================================
        // Based on this https://stackoverflow.com/questions/61443542/reading-tsv-file-in-java
        // Not tested yet, maybe not finished yet either
        // ========================================

        ArrayList<String> data = new ArrayList();

        try (BufferedReader TSVReader = new BufferedReader(new FileReader(filePath + fileName))) {
            String line = null;

            while ((line = TSVReader.readLine()) != null) {
                data.add(line);
            }

        }

        catch (Exception e) {
            System.out.println("Something went wrong with trying to load or read " + fileName);
            System.out.println(e);
        }

        return data;
    }
}
