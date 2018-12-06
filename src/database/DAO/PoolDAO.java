package database.DAO;

import account.Administrator;
import database.Database;
import tournament.Tournament;
import tournament.pool.Pool;

import java.sql.*;
import java.util.Objects;

public class PoolDAO {

    void insertPool(Tournament tournament, Connection con) {


        try {
            String query = "INSERT INTO Pool (yearGroup, skillLevel, idTournamentPool, matchDuration) VALUES(?, ?, ?, ?)";

            for (int i = 0; i < tournament.getPoolList().size(); i++) {
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, tournament.getPoolList().get(i).getYearGroup());
                stmt.setString(2, tournament.getPoolList().get(i).getSkillLevel());
                stmt.setInt(3, Objects.hash(tournament.getName()));
                stmt.setInt(4, tournament.getPoolList().get(i).getMatchDuration());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int findPoolID(Pool pool, Connection con) {
        try{
            String sql = "select * from Pool where yearGroup = " + pool.getYearGroup() + " AND skillLevel = '" + pool.getSkillLevel() + "'";

            ResultSet set = con.createStatement().executeQuery(sql);

            if (set.next()) {
                return set.getInt("idPool");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
