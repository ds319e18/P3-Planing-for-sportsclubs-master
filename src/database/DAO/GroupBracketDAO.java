package database.DAO;

import database.Database;
import tournament.Match;
import tournament.Tournament;
import tournament.TournamentType;
import tournament.pool.Pool;
import tournament.pool.bracket.GroupBracket;
import tournament.pool.bracket.StandardGroupPlay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class GroupBracketDAO {

    // Inserting groupbracket
    public void insertGroupBracket(Tournament tournament) {
        PoolDAO poolSQL = new PoolDAO();

        try(Connection con = Database.connect()) {
            String query = "INSERT INTO GroupBracket (idPoolGroupBracket, matchesPrOpponent, amountOfGroups) VALUES(?, ?, ?)";

            for (Pool pool : tournament.getPoolList()) {
                PreparedStatement stmt = con.prepareStatement(query);
                int poolID = poolSQL.findPoolID(pool, tournament, con);
                stmt.setInt(1 , poolID);
                stmt.setInt(2 , pool.getGroupBracket().getMatchesPrTeamAgainstOpponentInGroup());
                stmt.setInt(3, pool.getGroupBracket().getAmountOfGroups());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getGroupBracket(Tournament tournament, Pool pool, Connection con) {
        PoolDAO poolSQL = new PoolDAO();
        try{
            int poolID = poolSQL.findPoolID(pool, tournament, con);

            String sql = "select * from GroupBracket where idPoolGroupBracket = " + poolID;

            ResultSet set = con.createStatement().executeQuery(sql);

            if (set.next()) {
                pool.addGroupBracket(new StandardGroupPlay(set.getInt("amountOfGroups")));
                pool.getGroupBracket().getMatches().addAll(getMatches(tournament, pool, con));
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Match> getMatches(Tournament tournament, Pool pool, Connection con) {
        ArrayList<Match> matches = new ArrayList<>();
        PoolDAO poolSQL = new PoolDAO();
        TeamDAO teamSQL = new TeamDAO();

        try{
            int poolID = poolSQL.findPoolID(pool, tournament, con);
            String type = "Group";

            String query = "select * from MatchTable where idPoolMatchTable = " + poolID + " AND name = '" + type + "'";

            ResultSet set = con.createStatement().executeQuery(query);

            while (set.next()) {
                matches.add(new Match.Builder(set.getInt("duration"))
                        .setName(set.getString("name") + " Match " + set.getInt("number"))
                        .setFirstTeam(teamSQL.getTeam(set.getInt("idFirstTeam"), con))
                        .setSecondTeam(teamSQL.getTeam(set.getInt("idSecondTeam"), con))
                        .setFinished(false)
                        .build());
            }

            return matches;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
