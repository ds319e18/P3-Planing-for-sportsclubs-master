package database;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Properties;

public class Database {
    Connection con;

    // This method is used to get connection to the database, this method is static so there is no need to
    // create a object of Database, but just call this method and it will return a Connection object
    public static Connection connect() {
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
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Tournament", properties);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return con;
    }

    /*public void setName() {

        try {
            Statement statement = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS what" +
                    "(name VARCHAR(50)," +
                    "last VARCHAR(50))";
            statement.executeUpdate(sql);
            String query = "insertField into what(name)" + " values(?)";
            PreparedStatement prepare = con.prepareStatement(query);
            prepare.setString(1, "Frederik");
            prepare.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }*/
}
