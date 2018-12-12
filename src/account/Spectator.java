package account;

import tournament.Tournament;

import java.util.ArrayList;

public class Spectator extends User{
    private ArrayList<Tournament> tournaments;
    private int id;

    public Spectator(int id) {
        this.id = id;
    }

    @Override
    public void setTournaments(ArrayList<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public ArrayList<Tournament> getTournaments() {
        return tournaments;
    }

    public int getId() {
        return id;
    }
}
