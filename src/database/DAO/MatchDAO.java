package database.DAO;

import database.Database;
import tournament.Match;
import tournament.Team;
import tournament.Tournament;
import tournament.matchschedule.MatchDay;
import tournament.pool.Pool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class MatchDAO {

    // Inserting matches for either groupbracket og playoff bracket
    public void insertMatches(Tournament tournament, int accountID, ArrayList<Match> matchesToBeInserted) {
        TeamDAO teamSQL = new TeamDAO();
        TournamentDAO tournamentSQL = new TournamentDAO();

        try(Connection con = Database.connect()) {
            String query = "INSERT INTO MatchTable(name, duration, idFirstTeam, idSecondTeam, finishedBoolean, idTournamentMatch) VALUES(?, ?, ?, ?, ?, ?)";

                if (matchesToBeInserted.get(0).getFirstTeam().getName().equals("TBD")) {
                    teamSQL.insertTBDTeams(tournament, matchesToBeInserted, con);
                }

                for (Match match : matchesToBeInserted) {
                    int firstTeamID = teamSQL.findTeamID(match.getFirstTeam(), con);
                    int secondTeamID = teamSQL.findTeamID(match.getSecondTeam(), con);
                    int tournamentID = tournamentSQL.findTournamentID(tournament, accountID, con);

                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setString(1 , match.getName());
                    stmt.setInt(2 , match.getDuration());
                    stmt.setInt(3 , firstTeamID);
                    stmt.setInt(4 , secondTeamID);
                    stmt.setBoolean(5 , match.isFinished());
                    stmt.setInt(6 , tournamentID);
                    stmt.executeUpdate();
                }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Finding a specific id for a match using tournamentID and match name
    public int findMatchID(Match match, Tournament tournament, Connection con) {
        try {
            int tournamentID = Objects.hash(tournament.getName());

            String query = "select * from MatchTable where idTournamentMatch = " + tournamentID + " AND name = '" + match.getName() + "'";

            ResultSet set = con.createStatement().executeQuery(query);

            if (set.next()) {
                return set.getInt("idMatch");
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        // TODO Lav exception
        return 0;
    }

    // Giving a match a matchDayID so that we know which day the match is played
    public void updateMatchDayID(Tournament tournament) {
        MatchDayDAO matchDaySQL = new MatchDayDAO();

        try(Connection con = Database.connect()) {
            String query = "UPDATE MatchTable SET idMatchDayMatch = ? WHERE idMatch = ?";
            for (MatchDay day : tournament.getMatchSchedule().getMatchDays()) {
                int matchDayID = matchDaySQL.findMatchDayID(day, tournament, con);
                for (Match match : day.getMatches()) {
                    int matchID = findMatchID(match, tournament, con);
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setInt(1, matchDayID);
                    stmt.setInt(2, matchID);
                    stmt.executeUpdate();
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

        try(Connection con = Database.connect()) {
            int matchID = matchSQL.findMatchID(match, tournament, con);
            int firstTeamID = teamSQL.findTeamID(match.getFirstTeam(), con);
            int secondTeamID = teamSQL.findTeamID(match.getSecondTeam(), con);

            String query = "UPDATE MatchTabel SET idFirstTeam = ? AND idSecondTeam = ? WHERE matchID = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, firstTeamID);
            stmt.setInt(2, secondTeamID);
            stmt.setInt(3, matchID);
            stmt.executeUpdate();


        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
