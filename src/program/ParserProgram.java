package program;

import file_utils.*;
import parsers.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;


public class ParserProgram {

    /**
     * Parse all files
     */
    public void parseEverything() {

        // Actors.csv
        //parseFile("name_basics.tsv", "actors.csv", new ActorNameParser());

        // Ratings
        //parseFile("title_ratings.tsv", "ratings.csv", new RatingsParser());

        // TitlesAndActors
        //parseFile("name.basics", "titlesandactors.csv", new TitlesAndActorsParser());

        parseFile("title_basics.tsv", "countries.csv", new TitleParser());
    }

    /**
     * Parses a file and returns parsed data
     * @param fileName The file name to parse
     * @param outputData The list in which to insert the parsed data
     * @param parser The parser strategy to use
     * @return The parsed data
     */
    public static void parseFile(String fileName, Collection<String> outputData, ParserStrategy parser) {

        doParseProcess(fileName, parser, parsedLine -> {
            // Null means the parser thinks it should get filtered out
            if (parsedLine != null) {
                outputData.add(parsedLine);
            }
        });

    }

    /**
     * Parse a file and save it afterwards
     * @param fileName The file to parse
     * @param outputFileName The name for the output file
     * @param parser The parser strategy to use
     */
    public static void parseFile(String fileName, String outputFileName, ParserStrategy parser) {
        SaveFile saveFile = new SaveFile(outputFileName);

        doParseProcess(fileName, parser, parsedLine -> {
            // Null means the parser thinks it should get filtered out
            if (parsedLine != null) {
                saveFile.addLine(parsedLine);
            }
        });

        saveFile.save();
    }

    /**
     * Do the actual parsing process. ProcessParsedLineFunction is a function that gets called after every line parse.
     * @param fileName The file name to parse
     * @param parser The parser strategy to use
     * @param processParsedLineFunction The method to run on each parsed line, giving the parsed line as argument
     */
    private static void doParseProcess(String fileName, ParserStrategy parser, Consumer<String> processParsedLineFunction) {
        System.out.println("Trying to parse " + fileName);

        DataFile inputFile = new DataFile(fileName);

        String line = null;

        while((line = inputFile.readLine()) != null) {
            String parsedLine = parser.parse(line);

            processParsedLineFunction.accept(parsedLine);
        }

        System.out.println("Finished parsing " + fileName);
    }



}