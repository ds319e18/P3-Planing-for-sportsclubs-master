package tournament.pool;

import tournament.*;
import tournament.pool.bracket.GroupBracket;
import tournament.pool.bracket.PlayoffBracket;

import java.util.*;

public class Pool {
    private String name;
    private String skillLevel;
    private int yearGroup;
    private ArrayList<Team> teamList = new ArrayList<>();
    private GroupBracket groupBracket;
    private PlayoffBracket playoffBracket;
    private int matchDuration = 0;
    private String groupsVerificationStatus = "Ikke færdig";
    private String playOffVerificationStatus = "Ikke færdig";

    public void addGroupBracket(GroupBracket groupBracketType) {
        this.groupBracket = groupBracketType.createGroupBracket(teamList);
    }

    public void addPlayoffBracket(PlayoffBracket playoffBracketType) {
        this.playoffBracket = playoffBracketType.createPlayoffBracket(this.groupBracket, matchDuration);
    }

    public String getGroupCreationStatus() {
        if (groupBracket != null)
            return "Færdig";
        else
            return "Ikke færdig";
    }

    public String getGroupsVerificationStatus() {
        return groupsVerificationStatus;
    }

    public String getPlayOffCreationStatus() {
        if (playoffBracket != null && groupBracket.getAmountOfAdvancingTeamsPrGroup() != 0)
            return "Færdig";
        else
            return "Ikke færdig";
    }

    public String getPlayOffType() {
        return playoffBracket.getClass().getSimpleName();
    }

    public String getPlayOffVerificationStatus() {
        return playOffVerificationStatus;
    }

    public void setGroupsVerificationStatus(String statusString) {
        groupsVerificationStatus = statusString;
    }

    public void setPlayOffVerificationStatus(String statusString) {
        this.playOffVerificationStatus = statusString;
    }

    // Adding team to the correct pool
    public void addTeam(Team team) {
        int count;
        for (count = 1; count <= this.teamList.size(); count++) {
            Team tempTeam = new Team(team.getName() + " " + count, team.getYearGroup(), team.getSkillLevel());
            if (!this.teamList.contains(tempTeam)) {
                break;
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

    public PlayoffBracket getPlayoffBracket() {
        return playoffBracket;
    }

    public void setMatchDuration(String matchDuration) {
        this.matchDuration = Integer.parseInt(matchDuration);
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

