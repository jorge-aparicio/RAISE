
import BuildingDataSets.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class wordReplFeatures {
    private ArrayList<wordReplExample> featureSet;
    private ArrayList<ReplacedDataEntry> dataSet;

    public wordReplFeatures(ArrayList<ReplacedDataEntry> dataSet){
        this.dataSet = dataSet;
    }
    public void buildFeatureSet(){
        featureSet = new ArrayList<>();
        for (ReplacedDataEntry entry: dataSet) {

            if (entry.getOptions() != null) {
                wordReplExample tmp = new wordReplExample();

                tmp.setWord(entry.getOriginal_word());
                tmp.setWordLength(tmp.getWord().length());

                String ngrams = tmp.getWord().replace('-', ' ');
                tmp.setNumOriginalWord(ngrams.split(" ").length);

                tmp.setOptions(entry.getOptions());


                String[] options = tmp.getOptions().split(",");
                double avgLength = 0;
                double avgNumWords = 0;
                int numOptions = 0;
                for (String option : options
                ) {
                    numOptions++;

                    avgLength += option.length();

                    ngrams = option.replace('-', ' ');
                    avgNumWords += ngrams.split(" ").length;
                }
                avgLength = avgLength / numOptions;
                avgNumWords = avgNumWords / numOptions;

                tmp.setNumOptions(numOptions);
                tmp.setAvgOptionLength(avgLength);
                tmp.setAvgNumWords(avgNumWords);

                tmp.setWasReplaced(entry.getWas_replaced());
                featureSet.add(tmp);
            }
        }



        }


    public void saveDataSet(String filename){
        try {
            FileWriter myWriter = new FileWriter(filename);
            String labels = "original_word@length@number_of_words@options@average_option_length@average_number_words@number_of_options@was_replaced" +'\n';
            myWriter.write(labels);
            for (wordReplExample entry: featureSet) {
                myWriter.write(entry.toString());
            }
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/simplification?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Los_Angeles";
        String user = "";
        String password = "";

        ReplacementDataSet dataSet = new ReplacementDataSet(url,user,password);

        dataSet.genBaseTable();
        wordReplFeatures featureSet = new wordReplFeatures(dataSet.getDataSet());
        featureSet.buildFeatureSet();
        featureSet.saveDataSet("wordReplFeatures.txt");
    }

}
