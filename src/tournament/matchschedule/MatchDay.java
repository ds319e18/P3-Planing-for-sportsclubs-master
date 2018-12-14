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

    public LocalDate getDate() {
        return date;
    }

    public int getTimeBetweenMatches() { return timeBetweenMatches; }

    public void setTimeBetweenMatches(int timeBetweenMatches) {
        this.timeBetweenMatches = timeBetweenMatches;
    }

    public void setMatchesMix(ArrayList<Match> matches) {
        this.matches = matches;
    }

    private boolean sameTeamInMatches(Match match1, Match match2) {
        return (match2.getFirstTeam().equals(match1.getFirstTeam())
                || match2.getFirstTeam().equals(match1.getSecondTeam())
                || match2.getSecondTeam().equals(match1.getFirstTeam())
                || match2.getSecondTeam().equals(match1.getSecondTeam()));
    }

    // Check whether a new match will be placed within the timespan of a planned match.
    private boolean doMatchesOverlap(Match newMatch, Match plannedMatch, int fieldNumber) {
        return (plannedMatch.getTimeStamp().isBefore(this.fieldList.get(fieldNumber).getFieldEndTime()
                .plusMinutes(newMatch.getDuration())) &&                                                  // Efter starttidpunkt
                plannedMatch.getTimeStamp().isAfter(this.fieldList.get(fieldNumber).getFieldEndTime())) ||    // Før sluttidspunkt
                plannedMatch.getTimeStamp().equals(this.fieldList.get(fieldNumber).getFieldEndTime()) ||      // På starttidspunkt
                plannedMatch.getTimeStamp().equals(this.fieldList.get(fieldNumber).getFieldEndTime().plusMinutes(newMatch.getDuration()));// På sluttidpunkt
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
}
