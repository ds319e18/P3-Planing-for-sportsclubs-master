package account;

import tournament.Tournament;

import java.util.ArrayList;

public class Administrator extends account.User {
    private String username;
    private String password;
    private String name;
    private int id;
    private ArrayList<Tournament> tournamens;

    public Administrator(int id) {
        this.id = id;
    }

    public Administrator() {
    }

    public void setTournamens(ArrayList<Tournament> tournamens) {
        this.tournamens = tournamens;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Tournament> getTournamens() {
        return tournamens;
    }

    public int getId() {
        return id;
    }

    private boolean logIn() {
        return true;
    }
}
