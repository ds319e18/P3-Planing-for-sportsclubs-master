package account;

import tournament.Tournament;

import java.util.ArrayList;

public class User {
    private ArrayList<Tournament> tournaments;

    public User() {
        }


    public ArrayList<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(ArrayList<Tournament> tournaments) {
        this.tournaments = tournaments;
    }
}

