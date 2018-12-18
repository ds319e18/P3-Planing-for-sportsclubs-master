package tournament.matchschedule;

import javafx.scene.control.ComboBox;
import tournament.Match;
import tournament.pool.Pool;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class

MatchDay {
    private String name;
    private LocalTime startTime = LocalTime.MIN;
    private LocalTime endTime = LocalTime.MIN;
    private LocalDate date;
    private int timeBetweenMatches;
    private ArrayList<Match> matches;
    private ArrayList<Field> fieldList;

    public MatchDay(LocalDate date) {
        this.date = date;
        matches = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchDay matchDay = (MatchDay) o;
        return Objects.equals(getDate(), matchDay.getDate());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getDate());
    }

    // Getters
    public ArrayList<Match> getMatches() {
        return matches;
    }

    public ArrayList<Field> getFieldList() {
        return fieldList;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getTimeBetweenMatches() { return timeBetweenMatches; }

    // Setters
    public void setFieldList(ArrayList<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public void setStartTime(String startTimeText) {
        this.startTime = LocalTime.parse(startTimeText);
    }

    public void setEndTime(String endTimeText) {
        this.endTime = LocalTime.parse(endTimeText);
    }

    public void setName(String name) { this.name = name; }

    public void setTimeBetweenMatches(int timeBetweenMatches) {
        this.timeBetweenMatches = timeBetweenMatches;
    }

    public void setMatchesMix(ArrayList<Match> matches) {
        this.matches = matches;
    }

}
