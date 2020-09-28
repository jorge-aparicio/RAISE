package BuildingDataSets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class ReplacementDataSet {


    private String url, user, password;
    private ArrayList<ReplacedDataEntry> dataSet;
    private ArrayList<OptionsEntry> optionSet;

    public ReplacementDataSet(String url, String user, String password) throws ClassNotFoundException {
        this.url = url;
        this.user = user;
        this.password = password;
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    public ArrayList<ReplacedDataEntry> getDataSet(){
        return dataSet;

    }

    public void saveDataSet(String filename){
        try {
            FileWriter myWriter = new FileWriter(filename);
            String labels = "Session_ID@Text_ID@Original_Word@Replaced_Word@Was_Replaced@Replacement_Options@Text" +'\n';
            myWriter.write(labels);
            for (ReplacedDataEntry entry: dataSet) {
                myWriter.write(entry.toString());
            }
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void genBaseTable() {

        String query = "select session_id, text_id, word_clicked from clickedon where clicktype = 'word';";


        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            dataSet = new ArrayList<>();

            while (rs.next()) {
                ReplacedDataEntry tmp = new ReplacedDataEntry();

                tmp.setSession_id(rs.getString("session_id"));

                tmp.setText_id(rs.getString("text_id"));

                tmp.setOriginal_word(rs.getString("word_clicked"));


                dataSet.add(tmp);

            }


            // closes connection
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(ReplacementDataSet.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        query = "select session_id, text_id, original_word, replaced_word from replacedtext";


        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {


            while (rs.next()) {
                for (ReplacedDataEntry entry : dataSet
                ) {
                    if (entry.getWas_replaced() == 0) {
                        if (entry.getSession_id().equalsIgnoreCase(rs.getString("session_id")) && entry.getText_id().equalsIgnoreCase(rs.getString("text_id"))) {
                            if (entry.getOriginal_word().equalsIgnoreCase(rs.getString("original_word"))) {
                                entry.setReplaced_word(rs.getString("replaced_word"));
                                entry.setWas_replaced(1);
                            }
                        }
                    }
                }


            }


            // closes connection
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(ReplacementDataSet.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        query = "select session_id, text_id, text, options  from wordsOption;";


        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            optionSet = new ArrayList<>();
            while (rs.next()) {

                OptionsEntry entry = new OptionsEntry();

                entry.setSession_id(rs.getString("session_id"));
                entry.setText_id(rs.getString("text_id"));
                entry.setOriginal_word(rs.getString("text"));

                String tmp = rs.getString("options");

                String splitString[] = tmp.split(";");

                String formatString = "";
                for (String option : splitString
                ) {
                    String tmpArray[] = option.split("-D");
                    formatString += tmpArray[0] + ",";


                }
                entry.setOptions(formatString);
                optionSet.add(entry);


            }


            // closes connection
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(ReplacementDataSet.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        // match options from the WordsOption to the Replacement DataSet
        for (ReplacedDataEntry repEntry : dataSet
        ) {
            for (OptionsEntry opEntry : optionSet
            ) {
                if(opEntry.getMatched() == 0){
                    if(opEntry.getSession_id().equalsIgnoreCase(repEntry.getSession_id())){

                        if(opEntry.getText_id().equalsIgnoreCase(repEntry.getText_id())){

                            if(opEntry.getOriginal_word().equalsIgnoreCase(repEntry.getOriginal_word())){

                                repEntry.setOptions(opEntry.getOptions());
                                //opEntry.setMatched(1);
                            }
                        }
                    }
                }
            }
        }

        query = "select session_id, text_id, text_string  from simplifytext;";


        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                for (ReplacedDataEntry entry: dataSet
                ) {
                    if(entry.getSession_id().equalsIgnoreCase(rs.getString("session_id"))){
                        if(entry.getText_id().equalsIgnoreCase(rs.getString("text_id"))) {
                            entry.setText(rs.getString("text_string"));
                        }
                    }

                }

            }

            // closes connection
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(ReplacementDataSet.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }


    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/simplification?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Los_Angeles";
        String user = "jorgea";
        String password = "Archflash14";

        ReplacementDataSet dataSet = new ReplacementDataSet(url, user, password);

        dataSet.genBaseTable();
        dataSet.saveDataSet("repDataSet.txt");
    }

}
