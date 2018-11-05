package tournament;

import javafx.beans.property.SimpleStringProperty;

public class Team {
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

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleStringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public SimpleStringProperty skillLevelProperty() {
        return new SimpleStringProperty(skillLevel);
    }

    public int getYearGroup() {
        return yearGroup;
    }

    public void setYearGroup(int yearGroup) {
        this.yearGroup = yearGroup;
    }

    public SimpleStringProperty yearGroupProperty() {
        return new SimpleStringProperty(String.valueOf(yearGroup));
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public SimpleStringProperty contactProperty() {
        return new SimpleStringProperty(contact);
    }
}
