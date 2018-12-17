package database.DAO;

import database.Database;
import tournament.Team;
import tournament.Tournament;
import tournament.pool.Group;
import tournament.pool.Pool;

import java.sql.*;

public class GroupDAO {

    // Inserting groups in database
    public void insertGroup(Tournament tournament) {
        PoolDAO poolSQL = new PoolDAO();
        TeamDAO teamSQL = new TeamDAO();

        try (Connection con = Database.connect()) {
            String sql = "INSERT INTO GroupTable (idPoolGroup, groupNumber) VALUES(?, ?)";
            for (Pool pool : tournament.getPoolList()) {
                int poolID = poolSQL.findPoolID(pool, tournament, con);
                int count = 0;
                for (Group group : pool.getGroupBracket().getGroups()) {
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setInt(1, poolID);
                    stmt.setInt(2, count);
                    stmt.executeUpdate();
                    count++;
                }
            }
            teamSQL.updateGroupIdTeam(tournament, con);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to return group id
    public int findID(int poolID, int groupNumber, Connection con) {
        try {
            String query  = "SELECT * FROM GroupTable WHERE idPoolGroup = " + poolID + " AND groupNumber = " + groupNumber;
            ResultSet set = con.createStatement().executeQuery(query);

            if (set.next()) {
                return set.getInt("idGroup");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }
}
