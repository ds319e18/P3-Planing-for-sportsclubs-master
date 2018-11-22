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
            poolTeamList.get(i).setGroupNumber((i % this.amountOfGroups) + 1);
        }

        return this;
    }

    // We put the teams that advance through to the knockout play in a list of teams
    @Override
    public ArrayList<Team> advanceTeams() {
        ArrayList<Team> advancingTeams = new ArrayList<>();

        for (Group group : this.groups) {
            group.getTeamList().sort(new TeamPointsComp());
            int teamsAdded = 0;

            for (Team team : group.getTeamList()) {
                if (teamsAdded < this.advancingTeamsPrGroup) {
                    advancingTeams.add(team);
                    teamsAdded++;
                }
            }
        }

        return advancingTeams;
    }

    @Override
    public void createMatches(int matchDurationInMinutes) {
        int secondTeamIndex = 0;

        for (int iter = 0; iter < matchesPrTeamAgainstOpponentInGroup; iter++) {
            if (this.groups.isEmpty()) {
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
    public int getMatchesPrTeamAgainstOpponentInGroup() {
        return this.matchesPrTeamAgainstOpponentInGroup;
    }

    @Override
    public ArrayList<Group> getGroups() {
        return groups;
    }

    @Override
    public ArrayList<Match> getMatches() {
        return this.matches;
    }

    /*
    public void swapTeams(String name1, String name2) {
        Team team1 = new Team(name1, 0, "Z");
        Team team2 = new Team(name2, 0, "Z");
        for (Group group : this.getGroups()) {
            if (group.getTeamList().contains(new Team(name1, 0, "Z"))) {
                group.getTeamList().add(team2);
                group.getTeamList().remove(new Team(name1, 0, "Z"));

            }
            else if (group.getTeamList().contains(new Team(name2, 0, "Z"))) {
                group.getTeamList().add(team1);
                group.getTeamList().remove(new Team(name2, 0, "Z"));

            }
        }
    }
    */
}
