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
                stmt.setString(2, pool.getKnockoutBracket().getClass().getSimpleName());
                stmt.executeUpdate();
            }


        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getKnockoutBracket(Tournament tournament, Pool pool, Connection con) {
        PoolDAO poolSQL = new PoolDAO();
        try{
            int poolID = poolSQL.findPoolID(pool, tournament, con);

            String query = "select * from PlayoffBracket where idPoolPlayoff = " + poolID;

            ResultSet set = con.createStatement().executeQuery(query);

            if (set.next()) {
                if (set.getString("typePlayoff").equals("KnockoutPlay")) {
                    pool.addKnockoutBracket(new KnockoutPlay());
                } else if (set.getString("typePlayoff").equals("PlacementPlay")) {
                    pool.addKnockoutBracket(new PlacementPlay());
                } else if (set.getString("typePlayoff").equals("GoldAndBronzePlay")) {
                    pool.addKnockoutBracket(new GoldAndBronzePlay());
                }
            }

            pool.getKnockoutBracket().getMatches().addAll(getMatches(tournament, pool, con));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException r) {

        }
    }

    public ArrayList<Match> getMatches(Tournament tournament, Pool pool, Connection con) {
        ArrayList<Match> matches = new ArrayList<>();
        PoolDAO poolSQL = new PoolDAO();
        TeamDAO teamSQL = new TeamDAO();

        try{
            int poolID = poolSQL.findPoolID(pool, tournament, con);
            String typeOfMatch = "s";

            if (pool.getKnockoutBracket().getClass().getSimpleName().equals("KnockoutPlay")) {
                typeOfMatch = "Knockout";
            } else if (pool.getKnockoutBracket().getClass().getSimpleName().equals("PlacementPlay")) {
                typeOfMatch = "Placement";
            } else if (pool.getKnockoutBracket().getClass().getSimpleName().equals("GoldAndBronzePlay")) {
                typeOfMatch = "s";
            }


            String query = "select * from MatchTable where idPoolMatchTable = " + poolID + " AND name = '" + typeOfMatch + "'";


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
