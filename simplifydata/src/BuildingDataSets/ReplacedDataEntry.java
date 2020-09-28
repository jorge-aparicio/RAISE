package BuildingDataSets;

public class ReplacedDataEntry {

    private String session_id;
    private String text_id;
    private String original_word;
    private String replaced_word;
    private String options;
    private String text;
    private int was_replaced = 0;

    public ReplacedDataEntry() {

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

    public String getReplaced_word() {
        return replaced_word;
    }

    public void setReplaced_word(String replaced_word) {
        this.replaced_word = replaced_word;
    }

    public int getWas_replaced() {
        return was_replaced;
    }

    public void setWas_replaced(int was_replaced) {
        this.was_replaced = was_replaced;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        String tmp = text.replace('\n', ' ');
        this.text = '\"' + tmp + '\"';
    }

    @Override
    public String toString() {
        if (getWas_replaced() == 1)
            return
                    session_id + '@' +
                            text_id + '@' +
                            original_word + '@' +
                            replaced_word + '@' + options +'@' +
                            was_replaced +  '@' +text +'\n';
        else
            return
                    session_id + '@' +
                            text_id + '@' +
                            original_word + '@' +
                            original_word + '@' + options + '@' +
                            was_replaced + '@' + text +'\n';
    }
}
