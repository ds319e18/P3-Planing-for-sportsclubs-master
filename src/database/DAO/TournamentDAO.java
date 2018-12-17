package database.DAO;

import database.Database;
import tournament.Tournament;
import tournament.TournamentType;
import tournament.pool.Pool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class TournamentDAO {

    // Method to find alle tournaments in the database for the correct user
    public ArrayList<Tournament> getAllTournaments(int accountId) {
        PoolDAO poolSQL = new PoolDAO();
        FieldDAO fieldSQL = new FieldDAO();
        TeamDAO teamSQL = new TeamDAO();
        GroupBracketDAO groupBracketSQL = new GroupBracketDAO();
        PlayoffBracketDAO playoffBracketSQL = new PlayoffBracketDAO();
        MatchDAO matchSQL = new MatchDAO();
        MatchDayDAO matchDaySQL = new MatchDayDAO();
        ArrayList<Tournament> tournaments = new ArrayList<>();

        try (Connection con = Database.connect()) {
            Statement stmt = con.createStatement();
            String sql = "select * from Tournament where idAccountTournament = '" + accountId + "'";

            ResultSet set = stmt.executeQuery(sql);

            while (set.next()) {
                tournaments.add(new Tournament.Builder(set.getString("name"))
                        .setType(TournamentType.GroupAndKnockout)
                        .setActive(set.getBoolean("status"))
                        .setStartDate(set.getDate("startDate").toLocalDate())
                        .setEndDate(set.getDate("endDate").toLocalDate())
                        .build());
            }

            // Adding all pools and fields from database
            for (Tournament tournament : tournaments) {
                tournament.getPoolList().addAll(poolSQL.getAllPools(tournament, con));
                tournament.getFieldList().addAll(fieldSQL.getAllFields(tournament, con));
            }

            // Adding all teams from database and groupbracket and groups.
            for (Tournament tournament : tournaments) {
                for (Pool pool : tournament.getPoolList()) {
                    pool.getTeamList().addAll(teamSQL.getAllTeams(tournament, pool, con));

                    // Adding group bracket, and if there is, group matches
                    groupBracketSQL.getGroupBracket(tournament, pool, con);

                    // Adding knockoutbracket and knockout matches
                    playoffBracketSQL.getKnockoutBracket(tournament, pool, con);
                }
            }

            for (Tournament tournament : tournaments) {
                // Should be called when a match schedule has been created
                matchSQL.updateMatchesFromMatchsSchedule(tournament);
                matchDaySQL.addMatchToMatchDay(tournament);

            }

            // Kaldes til allersidst
            return tournaments;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tournaments;
    }

    // Method to insertField a tournaent created by the current user
    public void insertTournament(Tournament tournament, int accountID) {
        PoolDAO poolSQL = new PoolDAO();
        FieldDAO fieldDAO = new FieldDAO();

        // Converting tournament date from LocalDate to Date so it can fit to SQL database
        Date dateStart = Date.valueOf(tournament.getStartDate());
        Date dateEnd = Date.valueOf(tournament.getEndDate());

        try (Connection con = Database.connect()) {
            String query = "INSERT INTO Tournament (name, startDate, endDate, status, amountOfField, idAccountTournament, idTournament, tournamentType) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, tournament.getName());
            stmt.setDate(2, dateStart);
            stmt.setDate(3, dateEnd);
            stmt.setBoolean(4, tournament.isActive());
            stmt.setInt(5, tournament.getFieldList().size());
            stmt.setInt(6, accountID);
            stmt.setInt(7, Objects.hash(tournament.getName()));
            stmt.setString(8, tournament.getType().toString());

            stmt.executeUpdate();

            // Inserting pools and fields
            poolSQL.insertPool(tournament, con);
            fieldDAO.insertField(tournament, con);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
