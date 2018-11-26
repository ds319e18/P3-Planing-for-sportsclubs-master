package tournament.matchschedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.HashMap;

public class Field {
    private String name;
    private boolean occupied;
    private LocalTime fieldEndTime;

    public Field(String name, boolean occupied) {
        this.name = name;
        this.occupied = occupied;
    }

    public LocalTime getFieldEndTime() {
        return fieldEndTime;
    }

    public String getName() { return name; }

    public void setFieldEndTime(LocalTime fieldEndTime) {
        this.fieldEndTime = fieldEndTime;
    }

    public boolean isOccupied() {
        return occupied;
    }

    @Override
    public String toString() {
        return name;
    }
}
