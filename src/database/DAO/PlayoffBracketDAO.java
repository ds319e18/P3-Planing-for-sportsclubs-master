package database.DAO;

import database.Database;
import tournament.Tournament;
import tournament.pool.Pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayoffBracketDAO {

    public void insertPlayoffBracket(Tournament tournament, int accountID) {
        PoolDAO poolSQL = new PoolDAO();
        MatchDAO matchSQL = new MatchDAO();

        matchSQL.insertMatches(tournament, accountID, tournament.getAllPLayoffMatches());

        try(Connection con = Database.connect()){
            String query = "INSERT INTO PlayoffBracket (idPoolPlayoff) VALUES(?)";

            for (Pool pool : tournament.getPoolList()) {
                PreparedStatement stmt = con.prepareStatement(query);
                int poolID = poolSQL.findPoolID(pool, con);
                stmt.setInt(1, poolID);
                stmt.executeUpdate();
            }


        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
