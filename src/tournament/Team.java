package tournament;

import java.util.Comparator;
import java.util.Objects;

public class Team {
    private String name;
    private int points = 0;
    private String skillLevel;
    private int yearGroup;
    private String contact;
    private int groupNumber;

    public Team(String name, String skillLevel, int yearGroup, String contact) {
        this.name = name;
        this.skillLevel = skillLevel;
        this.yearGroup = yearGroup;
        this.contact = contact;
    }

    public Team(String name, int yearGroup, String skillLevel) {
        this.name = name;
        this.skillLevel = skillLevel;
        this.yearGroup = yearGroup;
    }

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public int getYearGroup() {
        return yearGroup;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return yearGroup == team.yearGroup &&
                Objects.equals(name, team.name) &&
                Objects.equals(skillLevel, team.skillLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, skillLevel, yearGroup);
    }
}
