package database.DAO;

import database.Database;
import tournament.Tournament;
import tournament.matchschedule.MatchSchedule;

import java.sql.*;
import java.util.Objects;

public class MatchScheduleDAO {

    // Inserting a matchschedule in database
    public void insertMatchSchedule(Tournament tournament) {
        MatchDayDAO matchDaySQL = new MatchDayDAO();

        try(Connection con = Database.connect()) {
            Date startDate = Date.valueOf(tournament.getStartDate());
            Date endDate = Date.valueOf(tournament.getEndDate());
            int tournamentID = Objects.hash(tournament.getName());

            String query = "INSERT INTO MatchSchedule(startDate, endDate, idTournamentMatchSchedule) VALUES(?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            stmt.setInt(3, tournamentID);
            stmt.executeUpdate();

            // Updating matcschuduleID in matchday
            matchDaySQL.updateMatchScheduleID(tournament, con);

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Finding match schedule id in the database
    public int findMatchScheduleID(Tournament tournament, Connection con) {
        int tournamentID = Objects.hash(tournament.getName());

        try {
            String query = "select * from MatchSchedule where idTournamentMatchSchedule = " + tournamentID;

            ResultSet set = con.createStatement().executeQuery(query);

            if (set.next()) {
                return set.getInt("idMatchSchedule");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

}
