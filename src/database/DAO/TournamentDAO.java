package database.DAO;

import database.Database;
import tournament.Tournament;
import tournament.TournamentType;
import tournament.pool.Pool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class TournamentDAO {

    // Method to find alle tournaments in the database for the correct user
    public ArrayList<Tournament> getAllTournaments(int accountId) {
        Connection con = Database.connect();
        ArrayList<Tournament> tournaments = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            String sql = "select * from Tournament where Account_id = '" + accountId + "'";

            ResultSet set = stmt.executeQuery(sql);

            while (set.next()) {
                tournaments.add(new Tournament.Builder(set.getString("name"))
                        .setType(TournamentType.Group)
                        .setActive(set.getBoolean("active"))
                        .setStartDate(set.getDate("startDate").toLocalDate())
                        .setEndDate(set.getDate("endDate").toLocalDate())
                        .build());
            }
            return tournaments;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tournaments;
    }

    // Method to insertField a tournaent created by the current user
    public void insertTournament(Tournament tournament, int accountID) {
        PoolDAO poolSQL = new PoolDAO();
        FieldDAO fieldDAO = new FieldDAO();

        // Converting tournament date from LocalDate to Date so it can fit to SQL database
        Date dateStart = Date.valueOf(tournament.getStartDate());
        Date dateEnd = Date.valueOf(tournament.getEndDate());

        try (Connection con = Database.connect()) {
            String query = "INSERT INTO Tournament (name, startDate, endDate, status, amountOfField, idAccountTournament, idTournament, tournamentType) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, tournament.getName());
            stmt.setDate(2, dateStart);
            stmt.setDate(3, dateEnd);
            stmt.setBoolean(4, tournament.isActive());
            stmt.setInt(5, tournament.getFieldList().size());
            stmt.setInt(6, accountID);
            stmt.setInt(7, Objects.hash(tournament.getName()));
            stmt.setString(8, tournament.getType().toString());

            stmt.executeUpdate();

            poolSQL.insertPool(tournament, con);
            fieldDAO.insertField(tournament, con);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int findTournamentID(Tournament tournament, int accountID, Connection con) {

        try {
            String sql = "select * from Tournament where idAccountTournament = " + accountID + " AND name = '" + tournament.getName() + "'";

            ResultSet set = con.createStatement().executeQuery(sql);

            if (set.next()) {
                return set.getInt("idTournament");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
