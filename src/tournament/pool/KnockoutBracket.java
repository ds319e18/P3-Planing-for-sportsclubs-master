package tournament.pool;

import tournament.matchschedule.*;
import tournament.*;
import tournament.Match;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class KnockoutBracket implements Bracket{
    private ArrayList<Match> matchList;
    private ArrayList<Team> advancingTeams;
    private KnockoutChoice choice;

    // This first part of the class deals with creating the tournament

    public KnockoutBracket(int numberOfTeams, ArrayList<Team> teams, String pickedChoice) {
        if (pickedChoice.equals(KnockoutChoice.Knockout.toString())) {
            choice = KnockoutChoice.Knockout;
        } else if (pickedChoice.equals(KnockoutChoice.Placement.toString())) {
            choice = KnockoutChoice.Placement;
        } else {
            choice = KnockoutChoice.PlacementAndKnockout;
        }
        createBracket(numberOfTeams, teams);
    }

    // This method only creates the number of matches in the knockout bracket, not the exact matches 
    @Override
    public void createBracket(int numberOfTeams, ArrayList<Team> teams) {
        // This variable holds the number of matches in the knockout stage of the tournament
        int numberOfMatches = 0;

        switch (choice) {
            case Knockout: {
                numberOfMatches = numberOfTeams - 1;

                for (int iter = 1; iter <= numberOfMatches; iter++) {
                    matchList.add(new Match(Integer.toString(iter), false));
                }
                break;
            }
            case Placement: {

                break;
            }

            case PlacementAndKnockout: {

                break;
            }

            default: {
                //may exception or error msg
                break;
            }
        }
    }



    //This next part of the class deals with updating the tournament while active

    // This method must be called whenever the user inputs tournament results in the group play
    public void updateKnockoutBracketTeams() {

    }

    @Override
    public void editBracket() {

    }
}
