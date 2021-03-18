package file_utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class LoaderListStrategy implements LoaderStrategy {
    private final String filePath = "input-files/";

    @Override
    public ArrayList<String> loadFile(String fileName) {

        ArrayList<String> data = new ArrayList();

        try (BufferedReader ListReader = new BufferedReader(new FileReader(filePath + fileName))) {
            String line = null;

            while ((line = ListReader.readLine()) != null) {
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
