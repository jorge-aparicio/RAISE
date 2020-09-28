public class wordReplExample {
    private String word;
    private int numOptions;
    private int wordLength;
    private double avgOptionLength;
    private int numOriginalWord;
    private double avgNumWords;
    private int wasReplaced;
    String options;
    //private String sentence;


    public wordReplExample(){

    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }



    public int getWordLength() {
        return wordLength;
    }

    public void setWordLength(int wordLength) {
        this.wordLength = wordLength;
    }

    public double getAvgOptionLength() {
        return avgOptionLength;
    }

    public void setAvgOptionLength(double avgOptionLength) {
        this.avgOptionLength = avgOptionLength;
    }

    public int getNumOriginalWord() {
        return numOriginalWord;
    }

    public void setNumOriginalWord(int numOriginalWord) {
        this.numOriginalWord = numOriginalWord;
    }

    public double getAvgNumWords() {
        return avgNumWords;
    }

    public void setAvgNumWords(double avgNumReplaceWord) {
        this.avgNumWords = avgNumReplaceWord;
    }

    public int getWasReplaced() {
        return wasReplaced;
    }

    public void setWasReplaced(int wasReplaced) {
        this.wasReplaced = wasReplaced;
    }

    public int getNumOptions() {
        return numOptions;
    }

    public void setNumOptions(int numOptions) {
        this.numOptions = numOptions;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    @Override
    public String toString() {
        char dmeter = '@';
        String data = getWord() + dmeter + getWordLength()+ dmeter + getNumOriginalWord() + dmeter + getOptions() + dmeter + getAvgOptionLength() + dmeter + getAvgNumWords() +dmeter+ getNumOptions() +dmeter + getWasReplaced() + '\n';
        return data;
    }
}