package database.DAO;

import database.Database;
import tournament.Match;
import tournament.Tournament;
import tournament.matchschedule.Field;
import tournament.pool.Pool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class FieldDAO {

    // Inserting fields in the database
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

    // Getting all fields for premade tournaments connected to a specific account
    public ArrayList<Field> getAllFields(Tournament tournament, Connection con) {
        ArrayList<Field> fields = new ArrayList<>();

        try {
            int tournamentID = Objects.hash(tournament.getName());
            String sql = "select * from Field where idTournamentField ='" + tournamentID + "'";
            Statement stmt = con.createStatement();

            ResultSet set = stmt.executeQuery(sql);

            while (set.next()) {
                fields.add(new Field(set.getString("name"), false));
            }

            return fields;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Finds field ID from database
    public int findFieldID(Tournament tournament, Match match, Connection con) {

        try {
            int tournamentID = Objects.hash(tournament.getName());
            String query = "select * from Field where idTournamentField = " + tournamentID + " AND name = '" + match.getField().getName() + "'";
            ResultSet set = con.createStatement().executeQuery(query);

            if (set.next()) {
                return set.getInt("idField");
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    // Finds field name in the database and returns it
    public String getFieldName(int fieldID, Connection con) {
        try {
            String fieldName;
            String sql = "select * from Field where idField =" + fieldID;
            Statement stmt = con.createStatement();

            ResultSet set = stmt.executeQuery(sql);
            if (set.next()) {
                fieldName = set.getString("name");
                return fieldName;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
