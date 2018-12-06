package tournament.pool.bracket;

import tournament.Match;
import tournament.Team;
import tournament.TeamPointsComp;
import tournament.pool.Group;

import java.util.ArrayList;

public class StandardGroupPlay extends GroupBracket {
    public StandardGroupPlay(int amountOfGroups) {
        super.setAmountOfGroups(amountOfGroups);
    }

    // This method creates the group bracket
    @Override
    public StandardGroupPlay createGroupBracket(ArrayList<Team> poolTeamList) {
        // The desired number of groups is created
        for (int i = 0; i < super.getAmountOfGroups(); i++) {
            super.getGroups().add(new Group());
        }
        // The teams is distributed in groups as equally as possible, switching between each group adding one team at a time
        for (int i = 0; i < poolTeamList.size(); i++) {
            super.getGroups().get(i % super.getAmountOfGroups()).addTeam(poolTeamList.get(i));
            poolTeamList.get(i).setGroupNumber((i % super.getAmountOfGroups()) + 1);
        }

        return this;
    }

    // We put the teams that advance through to the knockout play in a list of teams
    @Override
    public ArrayList<Team> advanceTeams() {
        ArrayList<Team> advancingTeams = new ArrayList<>();

        for (Group group : super.getGroups()) {
            group.getTeamList().sort(new TeamPointsComp());
            int teamsAdded = 0;

            for (Team team : group.getTeamList()) {
                if (teamsAdded < super.getAmountOfAdvancingTeamsPrGroup()) {
                    advancingTeams.add(team);
                    teamsAdded++;
                }
            }
        }

        return advancingTeams;
    }

    @Override
    public void createMatches(int matchDurationInMinutes) {
        int count = 1;
        int secondTeamIndex = 0;

        for (int iter = 0; iter < super.getMatchesPrTeamAgainstOpponentInGroup(); iter++) {
            if (super.getGroups().isEmpty()) {
                // throw exception
            } else {
                for (Group group : super.getGroups()) {
                    // Imagine to pointer vising the team and the groups. The one pointer chooses the home teams, and the other pointer chooses
                    // the away teams. This makes sure that we dont get any duplicate matches.
                    for (int firstTeamIndex = 0; firstTeamIndex < group.getTeamList().size() - 1; firstTeamIndex++) {
                        for (secondTeamIndex = firstTeamIndex + 1; secondTeamIndex < group.getTeamList().size(); secondTeamIndex++) {
                            super.getMatches().add(new Match.Builder(matchDurationInMinutes)
                                    .setFirstTeam(group.getTeamList().get(firstTeamIndex))
                                    .setSecondTeam(group.getTeamList().get(secondTeamIndex))
                                    .setFinished(false)
                                    .setName("Group Match " + count)
                                    .build());
                            count++;
                        }
                    }
                }
            }
        }
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
