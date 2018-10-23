package tournament.matchschedule;

public class Field {
    private String name;
    private boolean occupied;

    public Field(String name, boolean occupied) {
        this.name = name;
        this.occupied = occupied;
    }

    public boolean isOccupied() {
        return occupied;
    }

}
