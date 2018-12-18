package database.DAO;

import database.Database;
import tournament.Match;
import tournament.Tournament;
import tournament.pool.Pool;
import tournament.pool.bracket.GoldAndBronzePlay;
import tournament.pool.bracket.KnockoutPlay;
import tournament.pool.bracket.PlacementPlay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlayoffBracketDAO {

    // Inserting a playoff bracket in the database
    public void insertPlayoffBracket(Tournament tournament) {
        PoolDAO poolSQL = new PoolDAO();
        MatchDAO matchSQL = new MatchDAO();

        matchSQL.insertMatches(tournament, tournament.getAllPLayoffMatches());

        try(Connection con = Database.connect()){
            String query = "INSERT INTO PlayoffBracket (idPoolPlayoff, typePlayoff) VALUES(?, ?)";

            for (Pool pool : tournament.getPoolList()) {
                PreparedStatement stmt = con.prepareStatement(query);
                int poolID = poolSQL.findPoolID(pool, tournament, con);
                stmt.setInt(1, poolID);
                stmt.setString(2, pool.getPlayoffBracket().getClass().getSimpleName());
                stmt.executeUpdate();
            }


        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Getting a playoff bracket for a pool in a premade tournament
    public void getKnockoutBracket(Tournament tournament, Pool pool, Connection con) {
        PoolDAO poolSQL = new PoolDAO();
        try{
            int poolID = poolSQL.findPoolID(pool, tournament, con);

            String query = "select * from PlayoffBracket where idPoolPlayoff = " + poolID;

            ResultSet set = con.createStatement().executeQuery(query);

            if (set.next()) {
                if (set.getString("typePlayoff").equals("KnockoutPlay")) {
                    pool.addPlayoffBracket(new KnockoutPlay());
                } else if (set.getString("typePlayoff").equals("PlacementPlay")) {
                    pool.addPlayoffBracket(new PlacementPlay());
                } else if (set.getString("typePlayoff").equals("GoldAndBronzePlay")) {
                    pool.addPlayoffBracket(new GoldAndBronzePlay());
                }
            }

            // Clearing arraylist before getting matches since when creating a playoff bracket matches will be made
            pool.getPlayoffBracket().getMatches().clear();

            pool.getPlayoffBracket().getMatches().addAll(getMatches(tournament, pool, con));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException r) {

        }
    }

    // Getting all matches for a playoff bracket in a premade tournament
    public ArrayList<Match> getMatches(Tournament tournament, Pool pool, Connection con) {
        ArrayList<Match> matches = new ArrayList<>();
        PoolDAO poolSQL = new PoolDAO();
        TeamDAO teamSQL = new TeamDAO();

        try{
            int poolID = poolSQL.findPoolID(pool, tournament, con);
            String query;

            if (pool.getPlayoffBracket().getClass().getSimpleName().equals("KnockoutPlay")) {
                query = "select * from MatchTable where idPoolMatchTable = " + poolID + " AND name = '" + "Knockout" + "'";
            } else if (pool.getPlayoffBracket().getClass().getSimpleName().equals("PlacementPlay")) {
                query = "select * from MatchTable where idPoolMatchTable = " + poolID + " AND name = '" + "Placement" + "'";
            } else {
                query = "select * from MatchTable where idPoolMatchTable = " + poolID + " AND name = '" + "Gold" + "'" + "OR name = '" + "Bronze" + "'";
            }

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
