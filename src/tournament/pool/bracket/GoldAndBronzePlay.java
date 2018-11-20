package tournament.pool.bracket;

import tournament.Match;
import tournament.Team;

import java.util.ArrayList;
import java.util.HashMap;

public class GoldAndBronzePlay implements KnockoutBracket {
    private Match goldMatch;
    private Match bronzeMatch;
    private HashMap<String, Team> placements = new HashMap<>();

    @Override
    public KnockoutBracket createKnockoutBracket(GroupBracket groupBracket, int matchDurationInMinutes) {
        //Hardcoding skills on point
        goldMatch = new Match.Builder(matchDurationInMinutes)
                                        .setName("Final")
                                        .setFirstTeam(groupBracket.getGroups().get(0).getTeamList().get(0))
                                        .setSecondTeam(groupBracket.getGroups().get(1).getTeamList().get(0))
                                        .setFinished(false)
                                        .build();

        bronzeMatch = new Match.Builder(matchDurationInMinutes)
                                        .setName("Bronze Match")
                                        .setFirstTeam(groupBracket.getGroups().get(0).getTeamList().get(1))
                                        .setSecondTeam(groupBracket.getGroups().get(1).getTeamList().get(1))
                                        .setFinished(false)
                                        .build();
        return this;
    }

    @Override
    public ArrayList<Match> getMatches() {
        ArrayList<Match> matches = new ArrayList<>();
        matches.add(goldMatch);
        matches.add(bronzeMatch);
        return matches;
    }

    @Override
    public void createNextRound(ArrayList<Team> advancingTeams) {

    }

    @Override
    public ArrayList<Team> advanceTeams() {
        return null;
    }

    //Should only be called after the games are played
    public void setPlacements() {
        if (goldMatch.isFinished()) {
            this.placements.put("Gold", goldMatch.getWinner());
            this.placements.put("Silver", goldMatch.getLoser());
        } else {
            //Throw exception: games not played
        }

        if (bronzeMatch.isFinished()) {
            this.placements.put("Bronze", goldMatch.getWinner());
        } else {
            //Throw exception: games not played
        }
    }
}
