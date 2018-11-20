package tournament.pool.bracket;

import tournament.Match;
import tournament.Team;
import tournament.TeamPointsComp;
import tournament.pool.Group;

import java.util.ArrayList;

public class StandardGroupPlay implements GroupBracket {
    private ArrayList<Group> groups = new ArrayList<>();
    private ArrayList<Match> matches = new ArrayList<>();
    private int advancingTeamsPrGroup;
    private int matchesPrTeamAgainstOpponentInGroup;
    private int amountOfGroups;

    public StandardGroupPlay(int amountOfGroups) {
        this.amountOfGroups = amountOfGroups;
    }

    @Override
    // Use this method when choosing how many teams advance from each group
    public void setAdvancingTeamsPrGroup(int advancingTeamsPrGroup) {
        this.advancingTeamsPrGroup = advancingTeamsPrGroup;
    }

    @Override
    // Use this method when choosing how many matches against each opponent each team should have
    public void setMatchesPrTeamAgainstOpponentInGroup(int matchesPrTeamAgainstOpponentInGroup) {
        this.matchesPrTeamAgainstOpponentInGroup = matchesPrTeamAgainstOpponentInGroup;
    }


    // This method creates the group bracket
    @Override
    public StandardGroupPlay createGroupBracket(ArrayList<Team> poolTeamList) {
        // The desired number of groups is created
        for (int i = 0; i < this.amountOfGroups; i++) {
            this.groups.add(new Group());
        }
        // The teams is distributed in groups as equally as possible, switching between each group adding one team at a time
        for (int i = 0; i < poolTeamList.size(); i++) {
            this.groups.get(i % this.amountOfGroups).addTeam(poolTeamList.get(i));
        }

        return this;
    }

    // We iterate the first half of the groups (eg. 1 and 2 if there is 4). Then we put the advancing teams into different matches,
    // starting with number 1 of the group, then number 2 of the group. Then we iterate the last of the groups and put the advancing
    // teams into the matches, starting with the last teams to proceed (eg. if two teams proceed from the group stage then we should
    // put number 2 into the next match, then number 1 and continue like this for the last groups).
    @Override
    public ArrayList<Team> advanceTeams() {
        ArrayList<Team> advancingTeams = new ArrayList<>();

        int count = 0;
        for (Group group : this.groups) {
            int teamsAdded = 0;
            if (count < (this.groups.size() / 2)) {
                for (Team team : group.getTeamList()) {
                    if (teamsAdded < this.advancingTeamsPrGroup) {
                        advancingTeams.add(team);
                        teamsAdded++;
                    }
                }
            } else {
                for (int iter = advancingTeamsPrGroup; iter > 0; iter--) {
                    advancingTeams.add(group.getTeamList().get(iter - 1));
                }

            }
            count++;
        }

        return advancingTeams;
    }

    @Override
    public void createMatches(int matchDurationInMinutes) {
        int secondTeamIndex = 0;

        for (int iter = 0; iter < matchesPrTeamAgainstOpponentInGroup; iter++) {
            if (this.groups.isEmpty() ) {
                // throw exception
            } else {
                for (Group group : this.groups) {
                    // Imagine to pointer vising the team and the groups. The one pointer chooses the home teams, and the other pointer chooses
                    // the away teams. This makes sure that we dont get any duplicate matches.
                    for (int firstTeamIndex = 0; firstTeamIndex < group.getTeamList().size() - 1; firstTeamIndex++) {
                        for (secondTeamIndex = firstTeamIndex + 1; secondTeamIndex < group.getTeamList().size(); secondTeamIndex++) {
                            this.matches.add(new Match.Builder(matchDurationInMinutes)
                                    .setFirstTeam(group.getTeamList().get(firstTeamIndex))
                                    .setSecondTeam(group.getTeamList().get(secondTeamIndex))
                                    .setFinished(false)
                                    .setName("Group Match:" + '\t')
                                    .build());
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getAmountOfAdvancingTeamsPrGroup() {
        return this.advancingTeamsPrGroup;
    }

    @Override
    public int getAmountOfGroups() {
        return this.amountOfGroups;
    }

    @Override
    public ArrayList<Group> getGroups() {
        return groups;
    }

    @Override
    public ArrayList<Match> getMatches() {
        return this.matches;
    }

    public int getMatchesPrTeamAgainstOpponentInGroup() {
        return matchesPrTeamAgainstOpponentInGroup;
    }
}
