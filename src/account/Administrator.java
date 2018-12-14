package account;

import tournament.Tournament;

import java.util.ArrayList;

public class Administrator extends account.User {
    private String username;
    private String password;
    private String name;

    public Administrator(int id) {
        this.setId(id);
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    private boolean logIn() {
        return true;
    }
}
