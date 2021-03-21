import file_utils.*;
import parsers.ActorParser;
import parsers.Parser;
import parsers.RatingsParser;
import parsers.TestParser;


import java.util.ArrayList;
import java.util.List;


public class ParserProgram {

    private FileLoader loader;
    private FileSaver saver;

    private List<Parser> parsers;

    public ParserProgram() {
        loader = FileLoader.getInstance();
        saver = FileSaver.getInstance();

        parsers = new ArrayList<>();

        //parsers.add(new Parser("test.tsv", new TestParser(), "test.csv"));
        //parsers.add(new Parser("test.tsv", new TestParser()));
        //parsers.add(new Parser("ratings.tsv", new RatingsParser()));
        parsers.add(new Parser("title_principals.tsv", new ActorParser(), "title_principals.csv"));
//        parsers.add(new Parser("test.tsv", new TestParser()));
//        parsers.add(new Parser("movie.tsv", new MoviesParser()));
//        parsers.add(new Parser("actors.tsv", new ActorsParser()));
        parsers.add(new Parser("title_basics.tsv", new TitleParser(), "title_basics.csv"));
//        parsers.add(new Parser("test.tsv", new TestParser(), "test.csv"));

        // parsers.add(new Parser("ratings.tsv", new RatingsParser()));
        // parsers.add(new Parser("movies.tsv", new MoviesParser()));

    }

    /**
     * Parse all files
     */
    public void parseEverything() {

        final SaverStrategy saverStrategy = new SaverCSVStrategy();

        for (Parser parser : parsers) {

            System.out.println("Trying to parse " + parser.getFileName());

            final String fileName = parser.getFileName();
            List<String> data;

            if (fileName.endsWith(".tsv")) {
                data = loader.loadFile(fileName, new LoaderTSVStrategy());
            } else if(fileName.endsWith(".list")) {
                data = loader.loadFile(fileName, new LoaderListStrategy());

            }

            else {
                // Check for other kinds of files and strategies, otherwise:

                System.out.println("File " + fileName + " is not supported");
                continue; // Don't try to parse an invalid value
            }

            data = parser.parse(data);

            saver.saveFile(parser.getOutputFileName(), data, saverStrategy);

            System.out.println("Finished parsing " + parser.getFileName());
        }

    }


}
