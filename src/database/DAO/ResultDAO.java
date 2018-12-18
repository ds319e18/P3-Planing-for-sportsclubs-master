package database.DAO;

import database.Database;
import tournament.Match;
import tournament.Tournament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResultDAO { // Future work
    MatchDAO matchSQL = new MatchDAO();

    public void insertResult(Match match, Tournament tournament, int firstTeamScore, int secondTeamScore) {

        try(Connection con = Database.connect()) {
            String query = "INSERT INTO Result(idMatchResult, firstTeamScore, secondTeamScore) VALUES(?, ?, ?)";
            int matchID = matchSQL.findMatchID(match, tournament, con);
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, matchID);
            stmt.setInt(2, firstTeamScore);
            stmt.setInt(3, secondTeamScore);
            stmt.executeUpdate();

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
