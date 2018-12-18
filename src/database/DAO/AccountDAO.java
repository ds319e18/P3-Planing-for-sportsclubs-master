package database.DAO;

import account.Administrator;
import database.Database;

import java.sql.*;
import java.util.Objects;

public class AccountDAO {

    public boolean findAccount(String username, String password) {
        try(Connection con = Database.connect()) {
            Statement stmt = con.createStatement();

            int passwordHashed = Objects.hash(password);

            String query = "SELECT * FROM Account WHERE username = '" + username + "'" + "AND password = " + passwordHashed;
            ResultSet set = stmt.executeQuery(query);

            return set.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}
