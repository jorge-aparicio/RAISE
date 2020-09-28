
import BuildingDataSets.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class wordOrderFeatures {
    private ArrayList<wordOrderExample> featureSet;
    private ArrayList<WordOrderEntry> dataSet;

    public wordOrderFeatures(ArrayList<WordOrderEntry> dataSet){
        this.dataSet = dataSet;
    }
    public void buildFeatureSet(){
        featureSet = new ArrayList<>();
        for (WordOrderEntry entry: dataSet) {

            if (entry.getOptions() != null) {
                wordOrderExample tmp = new wordOrderExample();

                tmp.setWord(entry.getOriginal_word());
                tmp.setWordLength(tmp.getWord().length());

                tmp.setReplacement(entry.getReplaced_word());
                tmp.setReplaceLength(entry.getReplaced_word().length());

                String ngrams = tmp.getWord().replace('-', ' ');
                tmp.setNumOriginalWord(ngrams.split(" ").length);


                ngrams = tmp.getReplacement().replace('-', ' ');
                tmp.setNumReplaceWord(ngrams.split(" ").length);
                tmp.setWasReplaced(1);
                tmp.setOptions(entry.getOptions());
                featureSet.add(tmp);


                String[] options = entry.getOptions().split(",");
                for (String option : options
                ) {
                    if (!option.equalsIgnoreCase(tmp.getReplacement())) {
                        wordOrderExample unusedOption = new wordOrderExample();

                        unusedOption.setWord(entry.getOriginal_word());
                        unusedOption.setWordLength(unusedOption.getWord().length());
                        unusedOption.setReplacement(option);
                        unusedOption.setReplaceLength(option.length());

                        ngrams = unusedOption.getWord().replace('-', ' ');
                        unusedOption.setNumOriginalWord(ngrams.split(" ").length);

                        ngrams = tmp.getReplacement().replace('-', ' ');
                        unusedOption.setNumReplaceWord(ngrams.split(" ").length);
                        unusedOption.setWasReplaced(0);
                        unusedOption.setOptions(entry.getOptions());
                        featureSet.add(unusedOption);
                    }
                }
            }
        }
    }

    public void saveDataSet(String filename){
        try {
            FileWriter myWriter = new FileWriter(filename);
            String labels = "original_word@length@number_of_words@replaced_word@replacement_length@replacement_number_of_words@was_replaced@options_list" +'\n';
            myWriter.write(labels);
            for (wordOrderExample entry: featureSet) {
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

        WordOrderDataSet dataSet = new WordOrderDataSet(url,user,password);

        dataSet.genBaseTable();
        wordOrderFeatures featureSet = new wordOrderFeatures(dataSet.getDataSet());
        featureSet.buildFeatureSet();
        featureSet.saveDataSet("wordOrderFeatures.txt");
    }

}
