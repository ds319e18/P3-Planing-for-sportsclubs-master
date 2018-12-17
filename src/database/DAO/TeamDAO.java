package database.DAO;

import com.sun.jdi.ArrayReference;
import database.Database;
import tournament.Match;
import tournament.Team;
import tournament.Tournament;
import tournament.pool.Group;
import tournament.pool.Pool;

import java.sql.*;
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


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Inserting all teams in a tournament
    public void insertTeam(Tournament tournament) {
        try (Connection con = Database.connect()) {
            String query = "INSERT INTO Team (name, yearGroup, skillLevel, phonenumber, idTournamentTeam) VALUES(?, ?, ?, ?, ?)";

            for (Pool pool : tournament.getPoolList()) {
                for (Team team : pool.getTeamList()) {
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setString(1, team.getName());
                    stmt.setInt(2, team.getYearGroup());
                    stmt.setString(3, team.getSkillLevel());
                    stmt.setString(4, team.getPhoneNum());
                    stmt.setInt(5, Objects.hash(tournament.getName()));

                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Finding a team id in database
    public int findTeamID(Team team, Connection con) {
        try {
            String sql = "select * from Team where name = '" + team.getName() + "'" + "AND skillLevel = '" + team.getSkillLevel() + "'" + "AND yearGroup =" + team.getYearGroup();
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

    // When groups has been created, we need to update in the database which group a match is in, therefore
    // we update groupId field in the matchTable
    public void updateGroupIdTeam(Tournament tournament, Connection con) {
        GroupDAO groupSQL = new GroupDAO();
        PoolDAO poolSQL = new PoolDAO();
        int poolID;
        int groupID;
        int teamID;
        int groupNumber;

        try {
            // Going through all groups in all pools and adding group id to the team in the chosen group
            for (Pool pool : tournament.getPoolList()) {
                poolID = poolSQL.findPoolID(pool, tournament, con);
                groupNumber = 0;
                for (Group group : pool.getGroupBracket().getGroups()) {
                    groupID = groupSQL.findID(poolID, groupNumber, con);
                        for (Team team : group.getTeamList()) {
                        teamID = findTeamID(team, con);
                        String query = "UPDATE Team SET idGroupTeam = ? WHERE idTeam = ?";
                        PreparedStatement stmt = con.prepareStatement(query);
                        stmt.setInt(1, groupID);
                        stmt.setInt(2, teamID);
                        stmt.executeUpdate();
                    }
                    groupNumber++;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to get all teams from a specific tournament and a specific pool from the database
    public ArrayList<Team> getAllTeams(Tournament tournament, Pool pool, Connection con) {
        ArrayList<Team> teams = new ArrayList<>();

        try {
            int tournamentID = Objects.hash(tournament.getName());
            String sql = "select * from Team where idTournamentTeam = " + tournamentID + " AND skillLevel = '" + pool.getSkillLevel() + "'" +  "AND yearGroup =" + pool.getYearGroup();
            Statement stmt = con.createStatement();

            ResultSet set = stmt.executeQuery(sql);

            while (set.next()) {
                teams.add(new Team(set.getString("name"), set.getInt("yearGroup"), set.getString("skillLevel")));
            }

            return teams;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Getting a specific team in the database
    public Team getTeam(int teamID, Connection con) {
        try {
            String sql = "select * from Team where idTeam = " + teamID;
            ResultSet set = con.createStatement().executeQuery(sql);

            if (set.next()) {
                return new Team(set.getString("name"), set.getString("skillLevel"), set.getInt("yearGroup"), set.getString("phonenumber"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
