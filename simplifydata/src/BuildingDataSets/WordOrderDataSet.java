package BuildingDataSets;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
public class WordOrderDataSet {

    private String url, user, password;
    private ArrayList<WordOrderEntry> dataSet;

    public WordOrderDataSet(String url, String user, String password) throws ClassNotFoundException {
        this.url = url;
        this.user = user;
        this.password = password;
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    public ArrayList<WordOrderEntry> getDataSet(){
        return dataSet;
    }

    public void saveDataSet(String filename){
        try {
            FileWriter myWriter = new FileWriter(filename);
            String labels = "Session_ID@Text_ID@Original_Word@Replaced_Word@Replacement_Options@Placement_Number@Text" +'\n';
            myWriter.write(labels);
            for (WordOrderEntry entry: dataSet) {
                myWriter.write(entry.toString());
            }
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void genBaseTable(){

        String query = "select session_id, text_id, original_word, replaced_word, options_of_word from replacedtext;";


        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
             dataSet = new ArrayList<>();

            while (rs.next()) {
                WordOrderEntry tmp =  new WordOrderEntry();

                tmp.setSession_id(rs.getString("session_id"));

                tmp.setText_id(rs.getString("text_id"));

                tmp.setOriginal_word(rs.getString("original_word"));

                tmp.setReplaced_word(rs.getString("replaced_word"));

                tmp.setOptions(rs.getString("options_of_word"));

                tmp.getReplaced_placment();

                dataSet.add(tmp);

            }

            // closes connection
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(WordOrderDataSet.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        query = "select session_id, text_id, text_string  from simplifytext;";


        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                for (WordOrderEntry entry: dataSet
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

            Logger lgr = Logger.getLogger(WordOrderDataSet.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }





    }



    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/simplification?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Los_Angeles";
        String user = "";
        String password = "";

        WordOrderDataSet dataSet = new WordOrderDataSet(url,user,password);

        dataSet.genBaseTable();
        dataSet.saveDataSet("wordOrdDataSet.txt");
    }
}
