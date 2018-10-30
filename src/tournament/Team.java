package tournament;

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
}
