package tournament;

import tournament.pool.Pool;

import java.util.Date;
import java.util.List;

public class Tournament {
    private Date date;
    private boolean active;
    private List<Pool> poolList;


    // Create tournament
    public Tournament(Date date) {
        this.date = date;
    }

    private void editTournament() {

    }

    private void addTournament() {

    }

    public boolean isActive() {
        return active;
    }
}
