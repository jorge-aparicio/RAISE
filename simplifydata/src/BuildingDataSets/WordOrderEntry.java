package BuildingDataSets;

import java.util.ArrayList;

public class WordOrderEntry {
    private String session_id;
    private String text_id;
    private String original_word;
    private String replaced_word;
    private String options;
    private int replaced_placment = -1;
    private String text;

    public WordOrderEntry() {

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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    private void generateOrderNumber() {

        String[] orderList = options.split(",");
        int orderNum = 0;
        for (String option : orderList) {
            if (option.equalsIgnoreCase(replaced_word)) replaced_placment = orderNum;
            orderNum++;
        }
    }

    public int getReplaced_placment() {
        if (replaced_placment == -1)
            generateOrderNumber();
        return replaced_placment;
    }

    public void setText(String text) {
        String tmp = text.replace('\n', ' ');
        this.text = '\"' + tmp + '\"';
    }

    public String getText() {
        return text;
    }


    public String toString() {
        char dmeter = '@';
        String data = getSession_id() + dmeter + getText_id()+ dmeter +getOriginal_word() + dmeter + getReplaced_word() + dmeter + getOptions() + dmeter + getReplaced_placment() + dmeter + getText() + '\n';
        return data;
    }
}
