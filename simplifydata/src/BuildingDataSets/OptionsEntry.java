package BuildingDataSets;

public class OptionsEntry {
    private String session_id;
    private String text_id;
    private String original_word;
    private String options;
    private int matched = 0;


    public OptionsEntry() {

    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getText_id() {
        return text_id;
    }

    public void setText_id(String text_id) {
        this.text_id = text_id;
    }

    public String getOriginal_word() {
        return original_word;
    }

    public void setOriginal_word(String original_word) {
        this.original_word = original_word;
    }

    public int getMatched() {
        return matched;
    }

    public void setMatched(int matched) {
        this.matched = matched;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }




}
