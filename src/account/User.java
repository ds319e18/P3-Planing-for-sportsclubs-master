package account;

import tournament.Tournament;

import java.util.ArrayList;

public abstract class User {
    private ArrayList<Tournament> tournaments;
    private int id;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() { return id; }

    public ArrayList<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(ArrayList<Tournament> tournaments) {
        this.tournaments = tournaments;
    }
}

