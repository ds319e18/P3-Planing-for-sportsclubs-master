package database.DAO;

import database.Database;
import tournament.Tournament;

import java.sql.*;
import java.util.Objects;

public class FieldDAO {

    public void insertField(Tournament tournament, Connection con) {
        try{
                String query = "INSERT INTO Field (name, idTournamentField) VALUES(?, ?)";

                for (int i = 0; i < tournament.getFieldList().size(); i++) {
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setString(1, tournament.getFieldList().get(i).getName());
                    stmt.setInt(2, Objects.hash(tournament.getName()));

                    stmt.executeUpdate();
                }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
