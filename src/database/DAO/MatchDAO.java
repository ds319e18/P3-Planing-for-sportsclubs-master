package database.DAO;

import database.Database;
import tournament.Match;
import tournament.Team;
import tournament.Tournament;
import tournament.matchschedule.Field;
import tournament.matchschedule.MatchDay;
import tournament.pool.Pool;
import tournament.pool.bracket.GoldAndBronzePlay;
import tournament.pool.bracket.KnockoutPlay;
import tournament.pool.bracket.PlacementPlay;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class MatchDAO {

    // Inserting matches for either groupbracket og playoff bracket
    public void insertMatches(Tournament tournament, ArrayList<Match> matchesToBeInserted) {
        TeamDAO teamSQL = new TeamDAO();
        PoolDAO poolSQL = new PoolDAO();

        try (Connection con = Database.connect()) {
            String query = "INSERT INTO MatchTable(name, duration, idFirstTeam, idSecondTeam, finishedBoolean, idTournamentMatch, idPoolMatchTable, number) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

            if (matchesToBeInserted.get(0).getFirstTeam().getName().equals("TBD")) {
                teamSQL.insertTBDTeams(tournament, matchesToBeInserted, con);
            }

            int number;
            for (Match match : matchesToBeInserted) {
                String name = match.getName().split(" ")[0];
                number = Integer.parseInt(match.getName().split(" ")[2]);
                int firstTeamID = teamSQL.findTeamID(match.getFirstTeam(), con);
                int secondTeamID = teamSQL.findTeamID(match.getSecondTeam(), con);
                int tournamentID = Objects.hash(tournament.getName());
                Pool pool = tournament.findCorrectPool(match.getFirstTeam().getYearGroup(), match.getFirstTeam().getSkillLevel());
                int poolID = poolSQL.findPoolID(pool, tournament, con);

                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setInt(2, match.getDuration());
                stmt.setInt(3, firstTeamID);
                stmt.setInt(4, secondTeamID);
                stmt.setBoolean(5, match.isFinished());
                stmt.setInt(6, tournamentID);
                stmt.setInt(7, poolID);
                stmt.setInt(8, number);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Finding a specific id for a match using tournamentID and match name
    public int findMatchID(Match match, Tournament tournament, Connection con) {
        PoolDAO poolSQL = new PoolDAO();
        try {
            int tournamentID = Objects.hash(tournament.getName());
            Pool pool = tournament.findCorrectPool(match.getFirstTeam().getYearGroup(), match.getFirstTeam().getSkillLevel());
            int poolID = poolSQL.findPoolID(pool, tournament, con);
            int matchNumber = Integer.parseInt(match.getName().split(" ")[2]);
            String matchName = match.getName().split(" ")[0];

            String query = "select * from MatchTable where idTournamentMatch = " + tournamentID + " AND number = " + matchNumber + " AND name ='" + matchName + "'" + " AND idPoolMatchTable =" + poolID;
            ResultSet set = con.createStatement().executeQuery(query);

            if (set.next()) {
                return set.getInt("idMatch");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    // Giving a match a matchDayID so that we know which day the match is played
    public void updateMatchDayID(Tournament tournament) {
        MatchDayDAO matchDaySQL = new MatchDayDAO();
        FieldDAO fieldSQL = new FieldDAO();

        try (Connection con = Database.connect()) {
            //String query = "UPDATE MatchTable SET idMatchDayMatch = ? AND idField = ? AND timeStamp = ? WHERE idMatch = ?";
            for (MatchDay day : tournament.getMatchSchedule().getMatchDays()) {
                int matchDayID = matchDaySQL.findMatchDayID(day, tournament, con);
                for (Match match : day.getMatches()) {
                    int matchID = findMatchID(match, tournament, con);
                    int fieldID = fieldSQL.findFieldID(tournament, match, con);
                    Time matchTimeStamp = Time.valueOf(match.getTimeStamp().plusHours(1));

                    PreparedStatement stmt03 = con.prepareStatement("UPDATE MatchTable SET idMatchDayMatch = ?, timeStamp = ?, idField = ? WHERE idMatch = ?");
                    stmt03.setInt(1, matchDayID);
                    stmt03.setInt(3, fieldID);
                    stmt03.setTime(2, matchTimeStamp);
                    stmt03.setInt(4, matchID);

                    stmt03.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Updating a match first team and second team in the database. This is called when a playoff creates next round
    public void updateTeamInMatch(Tournament tournament, Match match) {
        MatchDAO matchSQL = new MatchDAO();
        TeamDAO teamSQL = new TeamDAO();

        try (Connection con = Database.connect()) {
            int matchID = matchSQL.findMatchID(match, tournament, con);
            int firstTeamID = teamSQL.findTeamID(match.getFirstTeam(), con);
            int secondTeamID = teamSQL.findTeamID(match.getSecondTeam(), con);

            String query = "UPDATE MatchTabel SET idFirstTeam = ? AND idSecondTeam = ? WHERE matchID = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, firstTeamID);
            stmt.setInt(2, secondTeamID);
            stmt.setInt(3, matchID);
            stmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // When a match schedule has been created in a tournament with matches, this will be called to updated the matches
    // in the database, so a match in the database gets a field and timestamp
    public void updateMatchesFromMatchsSchedule(Tournament tournament) {
        try (Connection con = Database.connect()) {
            MatchDAO matchSQL = new MatchDAO();
            FieldDAO fieldSQL = new FieldDAO();

            for (Match match : tournament.getAllMatches()) {
                int matchID = matchSQL.findMatchID(match, tournament, con);
                String query = "select * from MatchTable where idMatch = " + matchID;

                ResultSet set = con.createStatement().executeQuery(query);

                if (set.next()) {
                    String fieldName = fieldSQL.getFieldName(set.getInt("idField"), con);
                    int indexOfField = 0;

                    // Finder field i vores field liste
                    for (int i = 0; i < tournament.getFieldList().size(); i++) {
                        if (tournament.getFieldList().get(i).getName().equals(fieldName)) {
                            indexOfField = i;
                        }
                    }

                    LocalTime time = set.getTime("timeStamp").toLocalTime().minusHours(1);
                    match.setField(tournament.getFieldList().get(indexOfField));
                    match.setTimestamp(time);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Checking wether a match is in the current match day by their IDs
    public boolean checkIfMatchInMatchDay(int matchID, int matchDayID, Connection con) {
        try {
            String query = "select * from MatchTable where idMatch = " + matchID + " AND idMatchDayMatch =" + matchDayID;
            ResultSet set = con.createStatement().executeQuery(query);

            return set.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
