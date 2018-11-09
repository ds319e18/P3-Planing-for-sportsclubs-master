package tournament;

import java.util.Objects;

public class Team implements Comparable<Team>{
    private String name;
    private int points;
    private String skillLevel;
    private int yearGroup;
    private String contact;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public int compareTo(Team o) {
        return this.getName().compareTo(o.getName());
    }
}
