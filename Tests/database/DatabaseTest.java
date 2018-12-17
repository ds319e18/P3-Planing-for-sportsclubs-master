package database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatabaseTest {
    private static Connection con;

    @BeforeAll
    private void connect() {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "password");
        properties.setProperty("useSSL", "false");
        properties.setProperty("useUnicode", "true");
        properties.setProperty("useJDBCCompliantTimezoneShift", "true");
        properties.setProperty("useLegacyDatetimeCode", "false");
        properties.setProperty("serverTimezone", "UTC");
        properties.setProperty("allowPublicKeyRetrieval", "true");

        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Test", properties);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterAll
    private void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Testing connection to the database
    @Test
    void testConnection() {
        try {
            assertEquals("Test", con.getCatalog());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Inserting int to database
    private static void insertInt() {
        try {
            int intToTest = 10;
            PreparedStatement stmt = con.prepareStatement("INSERT INTO TestObjects (intTest) VALUES(?)");
            stmt.setInt(1, intToTest);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Testing that the int was inserted in the database
    @Test
    void testInt() {
        int test = 0;
        try {
            insertInt();

            String sql = "select * from TestObjects where intTest =" + 10;
            Statement stmt = con.createStatement();

            ResultSet set = stmt.executeQuery(sql);

            if (set.next()) {
                test = set.getInt("intTest");
            }

            // Removing int from database
            PreparedStatement stmt02 = con.prepareStatement("DELETE FROM TestObjects WHERE intTest =" + 10);
            stmt02.executeUpdate();

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(10, test);
    }

    // Inserting double to database
    private static void insertDouble() {
        try {
            double doubleToTest = 12.124;
            PreparedStatement stmt = con.prepareStatement("INSERT INTO TestObjects (doubleTest) VALUES(?)");
            stmt.setDouble(1, doubleToTest);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Testing that the int was inserted in the database
    @Test
    void testDouble() {
        Double test = 1.1;
        try {
            insertDouble();

            String sql = "select * from TestObjects where doubleTest =" + 12.124;
            Statement stmt = con.createStatement();

            ResultSet set = stmt.executeQuery(sql);

            if (set.next()) {
                test = set.getDouble("doubleTest");
            }

            // Removing int from database
            PreparedStatement stmt02 = con.prepareStatement("DELETE FROM TestObjects WHERE doubleTest =" + 12.124);
            stmt02.executeUpdate();

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(12.124, test, 0.1);
    }

    // Inserting int to database
    private static void insertString() {
        try {
            String stringToTest = "Jetsmark";
            PreparedStatement stmt = con.prepareStatement("INSERT INTO TestObjects (stringTest) VALUES(?)");
            stmt.setString(1, stringToTest);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Testing that the int was inserted in the database
    @Test
    void testString() {
        String test = "";
        try {
            insertString();

            String sql = "select * from TestObjects where stringTest ='" + "Jetsmark" + "'";
            Statement stmt = con.createStatement();

            ResultSet set = stmt.executeQuery(sql);

            if (set.next()) {
                test = set.getString("stringTest");
            }

            // Removing int from database
            PreparedStatement stmt02 = con.prepareStatement("DELETE FROM TestObjects WHERE stringTest ='" + "Jetsmark" + "'");
            stmt02.executeUpdate();

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        assertEquals("Jetsmark", test);
    }

    // Converting localTime (as used in our program) to time which is the datatype saved in the database
    private static void insertTime() {
        try {
            LocalTime test = LocalTime.of(10,12);
            // Have to +1 hour, in database it gets -1 hour
            Time timeInDb = Time.valueOf(test.plusHours(1));

            PreparedStatement stmt = con.prepareStatement("INSERT INTO TestObjects (timeTest) VALUES(?)");
            stmt.setTime(1, timeInDb);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Testing that the time was inserted in the database
    @Test
    void testTime() {
        LocalTime localTime = LocalTime.of(10,12);
        Time localTimeConverted = Time.valueOf(localTime);
        LocalTime test = LocalTime.of(0,0);
        try {
            insertTime();

            String sql = "select * from TestObjects where timeTest ='" + localTimeConverted + "'";
            Statement stmt = con.createStatement();

            ResultSet set = stmt.executeQuery(sql);

            if (set.next()) {
                test = set.getTime("timeTest").toLocalTime().minusHours(1);
            }

            // Removing time from database
            PreparedStatement stmt02 = con.prepareStatement("DELETE FROM TestObjects WHERE timeTest ='" + localTimeConverted + "'");
            stmt02.executeUpdate();

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(localTime, test);
    }

    private static void insertDate() {
        try {
            LocalDate localDate = LocalDate.of(2018, 6, 5).plusDays(1);
            Date date = Date.valueOf(localDate);

            PreparedStatement stmt = con.prepareStatement("INSERT INTO TestObjects (dateTest) VALUES(?)");
            stmt.setDate(1, date);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Testing that the date was inserted in the database
    @Test
    void testDate() {
        LocalDate localDate = LocalDate.of(2018, 6, 5);
        Date localDateConverted = Date.valueOf(localDate);
        LocalDate test = LocalDate.of(2018,1,1);
        try {
            insertDate();

            String sql = "select * from TestObjects where dateTest ='" + localDateConverted + "'";
            Statement stmt = con.createStatement();

            ResultSet set = stmt.executeQuery(sql);

            if (set.next()) {
                test = set.getDate("dateTest").toLocalDate();
                System.out.println(test);
            }

            // Removing date from database
            PreparedStatement stmt02 = con.prepareStatement("DELETE FROM TestObjects WHERE dateTest ='" + localDateConverted + "'");
            stmt02.executeUpdate();

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(localDate, test);
    }
}