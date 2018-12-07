package database.DAO;

import database.Database;
import tournament.Team;
import tournament.Tournament;
import tournament.pool.Group;
import tournament.pool.Pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class GroupDAO {
    public void insertGroup(Tournament tournament) {
        PoolDAO poolSQL = new PoolDAO();
        TeamDAO teamSQL = new TeamDAO();

        try (Connection con = Database.connect()){
            String sql = "INSERT INTO GroupTable (idPoolGroup, idTeamGroup, groupNumber) VALUES(?, ?, ?)";
            for (Pool pool : tournament.getPoolList()) {
                int poolID = poolSQL.findPoolID(pool, con);
                int count = 1;
                for (Group group : pool.getGroupBracket().getGroups()) {
                    for (Team team : group.getTeamList()) {
                        int teamID = teamSQL.findTeamID(team, con);
                        PreparedStatement stmt = con.prepareStatement(sql);
                        stmt.setInt(1, poolID);
                        stmt.setInt(2, teamID);
                        stmt.setInt(3, count);
                        stmt.executeUpdate();
                    }
                    count++;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
