package tournament.matchschedule;

import javafx.scene.control.TextField;
import tournament.Match;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MatchDay {
    private String name;
    private LocalTime startTime = LocalTime.MIN;
    private LocalTime endTime = LocalTime.MIN;
    private LocalDate date;
    private int timeBetweenMatches;
    private ArrayList<Match> matches;
    private ArrayList<Field> fieldList;


    public MatchDay(LocalDate date) {
        this.date = date;
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public TextField getEndTimeTextField() {
        return new TextField(endTime.toString());
    }

    public TextField getStartTimeTextField() {
        return new TextField(startTime.toString());
    }

    public void setFieldList(ArrayList<Field> fieldList) {
        this.fieldList = fieldList;
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

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setName(String name) { this.name = name; }

    public LocalDate getDate() {
        return date;
    }

    public void setTimeBetweenMatches(int timeBetweenMatches) {
        this.timeBetweenMatches = timeBetweenMatches;
    }

    public void setMatchesMix(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public void setMatchesNoMix(ArrayList<Match> inputMatches) {
        ArrayList<Match> outputMatches = new ArrayList<>();
        int fieldNumber;
        int matchCounter = 0;

        setFieldsEndTime();

        for (Match match : inputMatches) {
            fieldNumber = matchCounter % this.fieldList.size();
            // Tjekker at der ikke tilføjes kampe såldes at tiden overskrider den endtime som er valgt
            if (!((fieldList.get(fieldNumber).getFieldEndTime().plusMinutes(match.getDuration() + this.timeBetweenMatches).isAfter(this.endTime)))) {
                if (!match.isPlanned()) {
                    match.setTimestamp(this.fieldList.get(fieldNumber).getFieldEndTime());
                    match.setField(this.fieldList.get(fieldNumber));
                    match.setPlanned(true);
                    this.fieldList.get(fieldNumber).setFieldEndTime(this.fieldList.get(fieldNumber).getFieldEndTime().plusMinutes((match.getDuration() + this.timeBetweenMatches)));
                    outputMatches.add(match);
                }
            }
            matchCounter++;
        }

        this.matches = outputMatches;
    }

    private void setFieldsEndTime() {
        for (Field field : this.fieldList) {
            field.setFieldEndTime(this.startTime);
        }
    }

    @Override
    public String toString() {
        return "                   " + name + ": " + startTime + "-" + endTime + " " + date + '\n' +
                matches.toString().replace(",", "").replace("[", " ").replace("]", "");
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
