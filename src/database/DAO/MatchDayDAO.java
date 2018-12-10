package database.DAO;

import database.Database;
import tournament.Match;
import tournament.Tournament;
import tournament.matchschedule.MatchDay;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class MatchDayDAO {
    MatchDAO matchSQL = new MatchDAO();

    // Inserting all match days from the tournament in the database
    public void insertMatchDay(Tournament tournament) {

        try(Connection con = Database.connect()) {
            String query = "INSERT INTO MatchDay(name, startTime, endTime, timeBetweenMatches, idTournamentMatchDay, date) VALUES(?, ?, ?, ?, ?, ?)";

            // Running through all match day in the tournament
            for (MatchDay day : tournament.getMatchSchedule().getMatchDays()) {

                // Converting localtime to time, have to +1 hour because mysql makes i -1 hour
                // and converting Localdate to sql Date
                Time startTime = Time.valueOf(day.getStartTime().plusHours(1));
                Time endTime = Time.valueOf(day.getEndTime().plusHours(1));
                Date dateForDay = Date.valueOf(day.getDate());

                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, day.getName());
                stmt.setTime(2, startTime);
                stmt.setTime(3, endTime);
                stmt.setInt(4, day.getTimeBetweenMatches());
                stmt.setInt(5, Objects.hash(tournament.getName()));
                stmt.setDate(6, dateForDay);
                stmt.executeUpdate();
            }

            matchSQL.updateMatchDayID(tournament);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Finding the specific matchday ID
    public int findMatchDayID(MatchDay matchday, Tournament tournament, Connection con) {
        int tournamentID = Objects.hash(tournament.getName());

        try {
            String query = "select * from MatchDay where name = '" + matchday.getName() + "'" + "AND idTournamentMatchDay = " + tournamentID;
            ResultSet set = con.createStatement().executeQuery(query);

            if (set.next()) {
                return set.getInt("idMatchDay");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //TODO create exception
        return 0;
    }

    // Inserting a matchschedule ID into a matchday in the database so we know
    // whch match schedule a match day is in
    public void updateMatchScheduleID(Tournament tournament, Connection con) {
        MatchScheduleDAO matchScheduleSQL = new MatchScheduleDAO();

        try{
            int matchScheduleID = matchScheduleSQL.findMatchScheduleID(tournament, con);
            int tournamentID = Objects.hash(tournament.getName());

            String query = "UPDATE MatchDay SET idMatchScheduleMatchDay = ? WHERE idTournamentMatchDay = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, matchScheduleID);
            stmt.setInt(2, tournamentID);
            stmt.executeUpdate();

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // method that adds all matches to their currect matchDay from the database
    public void addMatchToMatchDay(Tournament tournament) {
        MatchDayDAO matchDaySQL = new MatchDayDAO();
        MatchDAO matchSQL = new MatchDAO();
        ArrayList<Match> matches = new ArrayList<>();

        try(Connection con = Database.connect()) {
            for (MatchDay day : tournament.getMatchSchedule().getMatchDays()) {
                day.setMatches();
                int matchDayID = matchDaySQL.findMatchDayID(day, tournament, con);

                for (Match match : tournament.getAllMatches()) {
                    int matchID = matchSQL.findMatchID(match, tournament, con);

                    if (matchSQL.checkIfMatchInMatchDay(matchID, matchDayID, con)) {
                        day.getMatches().add(match);
                    }
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
