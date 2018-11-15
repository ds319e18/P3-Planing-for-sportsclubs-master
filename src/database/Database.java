package database;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Properties;

public class Database {
    Connection con;

    // Connects the java program to the database, right now its a local database.
    public Database() {
        this.con = connect();
    }

    public Connection connect() {
        Connection con = null;

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "password");
        properties.setProperty("useSSL", "false");
        properties.setProperty("useUnicode", "true");
        properties.setProperty("useJDBCCompliantTimezoneShift", "true");
        properties.setProperty("useLegacyDatetimeCode", "false");
        properties.setProperty("serverTimezone", "UTC");
        properties.setProperty("allowPublicKeyRetrieval", "true");
        properties.setProperty("", "");
        properties.setProperty("", "");
        properties.setProperty("", "");
        properties.setProperty("", "");
        properties.setProperty("", "");

        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Demo", properties);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return con;
    }

    public void setName() {

        try {
            Statement statement = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS what" +
                    "(name VARCHAR(50)," +
                    "last VARCHAR(50))";
            statement.executeUpdate(sql);
            String query = "insert into what(name)" + " values(?)";
            PreparedStatement prepare = con.prepareStatement(query);
            prepare.setString(1, "Frederik");
            prepare.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
