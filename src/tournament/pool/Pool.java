package tournament.pool;

import tournament.Team;
import tournament.TeamNameComp;
import tournament.pool.bracket.GroupBracket;
import tournament.pool.bracket.KnockoutBracket;

import java.util.ArrayList;
import java.util.Objects;

public class Pool {
    private String name;
    private String skillLevel;
    private int yearGroup;
    private ArrayList<Team> teamList = new ArrayList<>();
    private GroupBracket groupBracket;
    private KnockoutBracket knockoutBracket;
    private int matchDuration;

    public void addGroupBracket(GroupBracket groupBracketType) {
        this.groupBracket = groupBracketType.createGroupBracket(teamList);
    }

    public void addKnockoutBracket(KnockoutBracket knockoutBracketType) {
        this.knockoutBracket = knockoutBracketType.createKnockoutBracket(this.groupBracket, matchDuration);
    }

    // Adding team to the correct pool
    public void addTeam(Team team) {
        int count = 1;
        for (Team createdTeams : this.teamList) {
            if (createdTeams.getName().equals(team.getName() + " " + Integer.toString(count))) {
                count++;
            }
        }

        team.setName(team.getName() + " " + Integer.toString(count));
        this.teamList.add(team);
        this.teamList.sort(new TeamNameComp());
    }


    // Removes a team by first finding the correct pool, then after removing the correct team in the pool.
    public void removeTeam(String name) {
        for (int i = teamList.size() - 1; i >= 0; i--) {
            if (teamList.get(i).getName().equals(name)) {
                teamList.remove(teamList.get(i));
            }
        }
        this.teamList.sort(new TeamNameComp());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pool pool = (Pool) o;
        return yearGroup == pool.yearGroup;
    }

    @Override
    public int hashCode() {
        return Objects.hash(yearGroup);
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public int getYearGroup() {
        return yearGroup;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Team> getTeamList() {
        return teamList;
    }

    public GroupBracket getGroupBracket() {
        return groupBracket;
    }

    public int getMatchDuration() {
        return matchDuration;
    }

    public KnockoutBracket getKnockoutBracket() {
        return knockoutBracket;
    }

    //This next part of the class deals with updating the tournament while active

    // Inner Builder-class
    public static class Builder {
        private String skillLevel;
        private int yearGroup;
        private int matchDurationInMinutes;

        public Builder setSkilllLevel(String skillLevel) {
            this.skillLevel = skillLevel;
            return this;
        }

        public Builder setYearGroup(int yearGroup) {
            this.yearGroup = yearGroup;
            return this;
        }

        public Builder setMatchDurationInMinutes(int matchDurationInMinutes) {
            this.matchDurationInMinutes = matchDurationInMinutes;
            return this;
        }

        public Pool build() {
            Pool pool = new Pool();
            pool.skillLevel = this.skillLevel;
            pool.yearGroup = this.yearGroup;
            if (skillLevel != null)
                pool.name = "U" + String.valueOf(pool.yearGroup) + " " + pool.skillLevel;
            pool.matchDuration = this.matchDurationInMinutes;
            return pool;
        }
    }
}

