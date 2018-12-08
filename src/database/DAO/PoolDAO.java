package database.DAO;

import account.Administrator;
import database.Database;
import tournament.Tournament;
import tournament.TournamentType;
import tournament.pool.Pool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class PoolDAO {

    // Inserting pool in database
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

    // Method to find pool id
    public int findPoolID(Pool pool, Tournament tournament, Connection con) {
        try{
            int tournamentID = Objects.hash(tournament.getName());
            String sql = "select * from Pool where yearGroup = " + pool.getYearGroup() + " AND skillLevel = '" + pool.getSkillLevel() + "'" + "AND idTournamentPool =" + tournamentID;

            ResultSet set = con.createStatement().executeQuery(sql);

            if (set.next()) {
                return set.getInt("idPool");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public ArrayList<Pool> getAllPools(Tournament tournament, Connection con) {
        ArrayList<Pool> pools = new ArrayList<>();

        try {
            int tournamentID = Objects.hash(tournament.getName());
            String sql = "select * from Pool where idTournamentPool ='" + tournamentID + "'";
            Statement stmt = con.createStatement();

            ResultSet set = stmt.executeQuery(sql);

            while (set.next()) {
                pools.add(new Pool.Builder()
                        .setSkilllLevel(set.getString("skillLevel"))
                        .setYearGroup(set.getInt("yearGroup"))
                        .setMatchDurationInMinutes(set.getInt("matchDuration"))
                        .build());
            }

            return pools;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
