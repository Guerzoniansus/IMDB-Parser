package file_utils;

import java.io.*;
import java.util.List;

public class SaverCSVStrategy implements SaverStrategy {

    private final String filePath = "output/";

    @Override
    public void saveFile(String fileName, List<String> data) {


        try {
            FileWriter  writer = new FileWriter(filePath + fileName);

            for (String line : data) {
                writer.append(line);
                writer.append("\n");
            }

            writer.flush();
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
