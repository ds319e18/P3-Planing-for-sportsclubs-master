package database;

import Account.User;
import account.Administrator;
import tournament.Tournament;
import tournament.TournamentType;

import java.sql.*;
import java.time.LocalDate;

public class AccountDAOImpl implements AccountDAO {
    private Connection con = Database.connect();
    @Override
    public Administrator findAccount(String username, String password) {
        TournamentDAO tournamentSQL = new TournamentDAOImpl();

        try {
            Statement stmt = con.createStatement();
            //String sql = "select * from Account where username = '" + username + "'" + "where password = '" + password + "'";
            String sql = "select * from Account where username = '" + username + "'";
            ResultSet set = stmt.executeQuery(sql);
            Administrator user = new Administrator();

            if (set.next()) {
                user.setName(set.getString("name"));
                user.setUsername(set.getString("username"));
                user.setId(set.getInt("idAccount"));

                user.setTournamens(tournamentSQL.getAllTournaments(user.getId()));

                System.out.println(user.getTournamens().get(0).getName());
                return user;

                // Finding the tournaments the user has created
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // TODO Create warning if something goes wrong or if the username is already occupied
    @Override
    public void createAccount(String usernames, String passwords, String name) {
        try {
            String query = "INSERT INTO Account (username, password, name) VALUES(?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, usernames);
            stmt.setString(2, passwords);
            stmt.setString(3, name);
            stmt.executeUpdate();
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



    }
}
