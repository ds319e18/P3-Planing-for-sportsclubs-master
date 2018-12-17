package tournament.pool.bracket;

import exceptions.IllegalAmountOfTeamsException;
import tournament.Match;
import tournament.Team;
import tournament.pool.Group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PlayoffGroupPlay extends PlayoffBracket { // Future work

    @Override
    public PlayoffBracket createPlayoffBracket(GroupBracket groupBracket, int matchDurationInMinutes) {

        return this;
    }

    @Override
    public void createNextRound(ArrayList<Team> advancingTeams) {

    }

    @Override
    public void calculateResults() {

    }
}
