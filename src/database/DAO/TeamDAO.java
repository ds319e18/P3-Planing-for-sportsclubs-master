package database.DAO;

import database.Database;
import tournament.Match;
import tournament.Team;
import tournament.Tournament;
import tournament.pool.Pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class TeamDAO {

    // Method to insert TBD teams to database
    public void insertTBDTeams(Tournament tournament, ArrayList<Match> TBDTeamsToAdd, Connection con) {

        try {
            String query = "INSERT INTO Team (name, yearGroup, skillLevel, idTournamentTeam) VALUES(?, ?, ?, ?)";

            // Creating a temp arraylist that containt first and secound team for every match that is iterated trough
            ArrayList<Team> temp = new ArrayList<>();

            // First adding the two teams in the temp arraylist
            for (Match match : TBDTeamsToAdd) {
                temp.add(0, match.getFirstTeam());
                temp.add(1, match.getSecondTeam());

                // Adding the two TBD teams in the arraylist
                for (int i = 0; i < 2; i++) {
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setString(1, temp.get(i).getName());
                    stmt.setInt(2, temp.get(i).getYearGroup());
                    stmt.setString(3, temp.get(i).getSkillLevel());
                    stmt.setInt(4, Objects.hash(tournament.getName()));
                    stmt.executeUpdate();
                }

            }


        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // Inserting all teams in a tournament
    public void insertTeam(Tournament tournament) {
        try(Connection con = Database.connect()){
            String query = "INSERT INTO Team (name, yearGroup, skillLevel, phonenumber, idTournamentTeam) VALUES(?, ?, ?, ?, ?)";

            for (Pool pool : tournament.getPoolList()) {
                for (Team team : pool.getTeamList()) {
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setString(1, team.getName());
                    stmt.setInt(2, team.getYearGroup());
                    stmt.setString(3, team.getSkillLevel());
                    stmt.setString(4, team.getContact());
                    stmt.setInt(5, Objects.hash(tournament.getName()));

                    stmt.executeUpdate();
                }
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int findTeamID(Team team, Connection con) {
        try{
            String sql = "select * from Team where name = '" + team.getName() + "'" + "AND skillLevel = '" + team.getSkillLevel() + "'";
            ResultSet set = con.createStatement().executeQuery(sql);

            if (set.next()) {
                return set.getInt("idTeam");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //TODO Throw en exception
        return 0;
    }
}
