package database;

import account.Administrator;
import tournament.Tournament;

import java.util.ArrayList;

public interface TournamentDAO {
    ArrayList<Tournament> getAllTournaments(int accountId);
}
