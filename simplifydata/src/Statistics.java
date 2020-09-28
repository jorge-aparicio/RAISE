

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Statistics {
    int numWordClicks = 0;
    int numWordReps = 0;
    int numGramClicks = 0;
    int numGramOptions = 0;
    boolean wordCountComplete = false, repCountComplete = false, gramCountComplete = false, gramOpCountComplete = false;

    double wordSimplificationRate;
    String url, user, password;

    /**
     * @param url
     * @param user
     * @param password
     */
    public Statistics(String url, String user, String password) throws ClassNotFoundException {
        this.url = url;
        this.user = user;
        this.password = password;
        Class.forName("com.mysql.cj.jdbc.Driver");
    }


    /**
     *
     */
    public void calcNumWordClicks() {
        // database query string
        String query = "select word_clicked from clickedon where clicktype = 'word'; ";

        // connects to database and runs query
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            // iterates through result and adds 1 for every row
            while (rs.next()) {
                numWordClicks++;

            }
            // closes connection
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Statistics.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        wordCountComplete = true;
    }

    /**
     * @return
     */
    public int getNumWordClicks() {
        if (wordCountComplete == false) {
            calcNumWordClicks();
        }
        return numWordClicks;
    }

    /**
     *
     */
    public void calcNumWordReps() {
        // database query string
        String query = "select replaced_word from replacedtext; ";

        // connects to database and runs query
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            // iterates through result and adds 1 for every row
            while (rs.next()) {
                numWordReps++;

            }
            // closes connection
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Statistics.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        repCountComplete = true;
    }

    /**
     * @return
     */
    public int getNumWordReps() {
        if (repCountComplete == false) {
            calcNumWordReps();
        }
        return numWordReps;
    }

    public double getWordSimplRate() {
        double wordReps = getNumWordReps();
        double wordClicks = getNumWordClicks();
        return wordReps / wordClicks;
    }

    /**
     *
     */
    public void calcNumGramClicks() {
        // database query string
        String query = "select word_clicked from clickedon where clicktype = 'grammar';";

        // connects to database and runs query
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            // iterates through result and adds 1 for every row
            while (rs.next()) {
                numGramClicks++;

            }
            // closes connection
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Statistics.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        gramCountComplete = true;
    }
    /**
     * @return
     */
    public int getNumGramClicks() {
        if (gramCountComplete == false) {
            calcNumGramClicks();
        }
        return numGramClicks;
    }



    public void calcNumGramOptions() {
        // database query string
        String query = "select options from grammarRules;";

        // connects to database and runs query
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            // iterates through result and adds 1 for every row
            while (rs.next()) {
                numGramOptions++;

            }
            // closes connection
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Statistics.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        gramOpCountComplete = true;
    }
    /**
     * @return
     */
    public int getNumGramOptions() {
        if (gramOpCountComplete == false) {
            calcNumGramOptions();
        }
        return numGramOptions;
    }

    public double gramReadRate() {
        double gramClicks = getNumGramClicks();
        double gramOptions = getNumGramOptions();
        return gramClicks / gramOptions;
    }

    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/simplification?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Los_Angeles";
        String user = "jorgea";
        String password = "Archflash14";
        DecimalFormat df = new DecimalFormat("0.00");
        Statistics stats = new Statistics(url,user,password);

        double simplRate = stats.getWordSimplRate();
        double gramRead = stats.gramReadRate();
        System.out.println(df.format(simplRate*100) + "% of word simplification suggestions are taken.");
        System.out.println(df.format(gramRead*100) + "% of grammar suggestions are read.");


    }
}