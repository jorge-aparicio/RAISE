

public class wordOrderExample {
    private String word;
    private String replacement;
    private int wordLength;
    private int replaceLength;
    private int numOriginalWord;
    private int numReplaceWord;
    private String options;
    private int wasReplaced;
    //private String sentence;


    public wordOrderExample(){

    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    public int getWordLength() {
        return wordLength;
    }

    public void setWordLength(int wordLength) {
        this.wordLength = wordLength;
    }

    public int getReplaceLength() {
        return replaceLength;
    }

    public void setReplaceLength(int replaceLength) {
        this.replaceLength = replaceLength;
    }

    public int getNumOriginalWord() {
        return numOriginalWord;
    }

    public void setNumOriginalWord(int numOriginalWord) {
        this.numOriginalWord = numOriginalWord;
    }

    public int getNumReplaceWord() {
        return numReplaceWord;
    }

    public void setNumReplaceWord(int numReplaceWord) {
        this.numReplaceWord = numReplaceWord;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public int getWasReplaced() {
        return wasReplaced;
    }

    public void setWasReplaced(int wasReplaced) {
        this.wasReplaced = wasReplaced;
    }

    @Override
    public String toString() {
        char dmeter = '@';
        String data = getWord() + dmeter + getWordLength()+ dmeter + getNumOriginalWord() + dmeter + getReplacement() + dmeter + getReplaceLength() + dmeter + getNumReplaceWord() + dmeter+getWasReplaced() +dmeter+ getOptions() + '\n';
        return data;
    }
}
