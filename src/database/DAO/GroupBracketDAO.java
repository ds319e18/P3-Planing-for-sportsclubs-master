package database.DAO;

import database.Database;
import tournament.Tournament;
import tournament.pool.Pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GroupBracketDAO {

    // Inserting groupbracket
    public void insertGroupBracket(Tournament tournament) {
        PoolDAO poolSQL = new PoolDAO();

        try(Connection con = Database.connect()) {
            String sql = "INSERT INTO GroupBracket (idPoolGroupBracket, matchesPrOpponent) VALUES(?, ?)";

            for (Pool pool : tournament.getPoolList()) {
                PreparedStatement stmt = con.prepareStatement(sql);
                int poolID = poolSQL.findPoolID(pool, con);
                stmt.setInt(1 , poolID);
                stmt.setInt(2 , pool.getGroupBracket().getMatchesPrTeamAgainstOpponentInGroup());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
