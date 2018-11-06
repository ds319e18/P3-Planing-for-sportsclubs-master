package tournament.pool;

import tournament.Team;

import java.util.ArrayList;
import java.util.List;

public interface Bracket {
    void editBracket();
    void createBracket(int seperations, ArrayList<Team> teams);
}
