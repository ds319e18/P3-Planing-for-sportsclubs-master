package database.DAO;

import account.Administrator;
import database.Database;

import java.sql.*;
import java.util.Objects;

public class AccountDAO {
    private Connection con = Database.connect();

    public Administrator findAccount(String username, String password) {
        TournamentDAO tournamentSQL = new TournamentDAO();

        try {
            Statement stmt = con.createStatement();

            int passwordHashed = Objects.hash(password);

            String sql = "select * from Account where username = '" + username + "'" + "AND password = " + passwordHashed;
            ResultSet set = stmt.executeQuery(sql);
            Administrator user = new Administrator();

            if (set.next()) {
                user.setName(set.getString("name"));
                user.setUsername(set.getString("username"));
                user.setId(set.getInt("idAccount"));

                user.setTournamens(tournamentSQL.getAllTournaments(user.getId()));

                return user;

                // Finding the tournaments the user has created
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // TODO kast exception
        return null;
    }

    // TODO skal slettes
    public void createAccount(String usernames, String passwords, String name) {
        try {
            String query = "INSERT INTO Account (username, password) VALUES(?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);

            int passwordHashed = Objects.hash(passwords);

            stmt.setString(1, usernames);
            stmt.setInt(2, passwordHashed);
            stmt.executeUpdate();
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int findAccountID(String username, String password) {
        try {
            Statement stmt = con.createStatement();

            int passwordHashed = Objects.hash(password);

            String sql = "select * from Account where username = '" + username + "'" + "AND password =" + passwordHashed;
            ResultSet set = stmt.executeQuery(sql);

            if (set.next()) {
                return set.getInt("idAccount");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //TODO Kast en exception
        return 0;
    }
}
