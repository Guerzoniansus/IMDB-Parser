package parsers;

import java.util.ArrayList;
import java.util.List;

public class ActorParser implements ParserStrategy{

    @Override
    public List<String> parse(List<String> data) {
        data.set(0, data.get(0).replace("tconst", "TitleID"));
        data.set(0, data.get(0).replace("nconst", "PersonID"));


        List<String> actorsList = new ArrayList<>();
        int count = 0;

        for (String line:data) {
            count++;
            System.out.println(count);
            if(line.contains("actor") || line.contains("actress") || line.equals(data.get(0))){
                String[] items = line.split("\t");
                actorsList.add(items[0] + "\t" + items[2] + "\t" + items[3]);
            }
        }




        actorsList.replaceAll(line -> {
            String[] items = line.split("\t"); // Splits per tab

            return String.join(",", items);
        });
        return actorsList;
    }
}
