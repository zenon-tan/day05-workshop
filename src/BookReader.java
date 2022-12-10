import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BookReader {

    public static void main(String[] args) {
        
        // Read file
        // Parse lines
        // For each line, seperate each category into a Key (Publisher name), and values (others)
        // Using a hashmap
        // Clean up the keys and look for duplicates
        //Write to file using key as filename.csv, and value as contents separated by commas

        String fileName = args[0];
        String outputDir = args[1];

        System.out.printf("Processing %s...\n", fileName);

        try {

            
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            Map<String, List<ArrayList<String>>> bookMap = new HashMap<>();

            String line;

            int testCounter = 0;

            

            while(null != (line = br.readLine())) {

                // System.out.println(line);
                
                String[] splitData = line.split(",");
                ArrayList<String> bookData = new ArrayList<String>();

                for(int i = 1; i < splitData.length; i++) {
                    

                    splitData[i] = splitData[i].trim();
                    bookData.add(splitData[i]);

                }

                String bookKey = bookData.get(10);
                bookData.remove(10);

                // System.out.println( bookKey + " / " + bookData);

                if(!bookMap.containsKey(bookKey)) {

                    List<ArrayList<String>> bookDataList = new ArrayList<ArrayList<String>>();
                    bookDataList.add(bookData);
                    bookMap.put(bookKey, bookDataList);

                } else {
                    bookMap.get(bookKey).add(bookData);
                }


                testCounter++;

                if(testCounter == 50) {
                    break;
                }


            }

            List<ArrayList<String>> titleData = bookMap.get("publisher");
            bookMap.remove("publisher");

            // String titleInString = titleData.toString().replace("[", "")
            // .replace("]", "").replace(", ", ",") + "\n";

            String titleInString = parseListToString(titleData.get(0));

            for(String key : bookMap.keySet()) {
                // System.out.println("------------------");
                // System.out.printf("%s\n", key);
                // System.out.printf("%s\n", bookMap.get(key));
                // System.out.println("------------------");

                String reformattedKey = key.replace(".", "").replace(" ", "_");

                String outputFileName = reformattedKey + ".csv";
                System.out.println("Writing " + outputFileName + " to src/" + outputDir + " ...");

                FileWriter fw = new FileWriter(new File(outputDir,outputFileName), false);
                BufferedWriter bfw = new BufferedWriter(fw);

                bfw.write(titleInString);


                for(ArrayList<String> data : bookMap.get(key)) {
                    // System.out.println(">>" + key.toUpperCase());
                    // System.out.println("*************");
                    // String parseToString = data.toString().replace("[", "")
                    // .replace("]", "").replace(", ", ",");

                    String parseToString = parseListToString(data);
                    // System.out.println(parseToString);
                    // System.out.println("*************");

                    bfw.write(parseToString);
                    bfw.flush();



                    }

                    fw.close();
                    bfw.close();
                }

            br.close();
            fr.close();

            

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String parseListToString(ArrayList<String> titleData) {

        String formattedString = titleData.toString().replace("[", "")
        .replace("]", "").replace(", ", ",") + "\n";

        return formattedString;
    }
    
}
