package tournament.matchschedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.HashMap;

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
