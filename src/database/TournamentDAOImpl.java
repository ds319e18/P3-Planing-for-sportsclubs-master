package database;

import account.Administrator;
import tournament.Tournament;
import tournament.TournamentType;
import tournament.pool.Group;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class TournamentDAOImpl implements TournamentDAO {
    @Override
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

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return tournaments;
    }
}
