package tournament.matchschedule;

import tournament.Match;
import tournament.pool.Pool;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

    public void setTimeBetweenMatches(int timeBetweenMatches) {
        this.timeBetweenMatches = timeBetweenMatches;
    }

    public int getTimeBetweenMatches() {
        return timeBetweenMatches;
    }

    public void setMatchesMix(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public void setNoMixedMatches(ArrayList<Pool> poolList) {
        ArrayList<Match> outputMatches = new ArrayList<>();
        int fieldNumber = 0;

        setFieldsEndTime();

        for (Pool pool : poolList) {
            ArrayList<Match> matches = new ArrayList<>();
            matches.addAll(pool.getGroupBracket().getMatches());
            matches.addAll(pool.getKnockoutBracket().getMatches());

            for (Match match : matches) {
                // Tjekker at der ikke tilføjes kampe såldes at tiden overskrider den endtime som er valgt
                if (fieldNumber < fieldList.size() &&
                        !((fieldList.get(fieldNumber).getFieldEndTime().plusMinutes(match.getDuration() + this.timeBetweenMatches).isAfter(this.endTime)))) {

                    if (!match.isPlanned()) {
                        fieldList.get(fieldNumber).setOccupied(true);
                        match.setTimestamp(this.fieldList.get(fieldNumber).getFieldEndTime()); // Tiden kampen skal spilles sættes.
                        match.setField(this.fieldList.get(fieldNumber));    // Kampens bane sættes.
                        match.setPlanned(true); // Kampen sættes til at være planlagt.
                        // MatchDay'ens
                        this.fieldList.get(fieldNumber).setFieldEndTime(this.fieldList.get(fieldNumber).getFieldEndTime()
                                .plusMinutes((match.getDuration() + this.timeBetweenMatches)));
                        outputMatches.add(match); // Kampen tilføjes til outputMatches.
                    }

                }
            }

            if (fieldNumber < fieldList.size() &&
                    fieldList.get(fieldNumber).isOccupied()) {
                fieldList.get(fieldNumber).setOccupied(false);
                fieldNumber++;
            }
        }
        this.matches = outputMatches;
    }

    public void setMatchesNoMix(ArrayList<Match> inputMatches) {
        ArrayList<Match> outputMatches = new ArrayList<>();
        ArrayList<Match> tempMatches = new ArrayList<>();
        int fieldNumber;
        int matchCounter = 0;

        setFieldsEndTime();

        do {
            for (Match match : inputMatches) {
                if (match.getFirstTeam().getName().equals("TBD")) {
                    break;
                }

                boolean canBePlanned = true;
                fieldNumber = matchCounter % this.fieldList.size();
                // Tjekker at kampen ikke allerede er tilføjet til en MatchDay
                if (!match.isPlanned()) {
                    // Tjekker at der ikke tilføjes kampe såldes at tiden overskrider den endtime som er valgt
                    if (!((fieldList.get(fieldNumber).getFieldEndTime().plusMinutes(match.getDuration() + this.timeBetweenMatches).isAfter(this.endTime)))) {
                        // Alle de planlagte kampe køres igennem.
                        for (Match inputMatch : outputMatches) {
                            // Undersøger om det samme hold optræder i de to kampe
                            // Hvis det samme hold optræder, må kampen ikke planlægges hvis kampene overlapper.
                            if (sameTeamInMatches(match, inputMatch) &&
                                    doMatchesOverlap(match, inputMatch, fieldNumber)) {
                                // Hvis kampene indeholder nogle ens hold og overlapper i tidsinterval,
                                // sættes canBePlanned til false, og kampen vil ikke blive planlagt.
                                canBePlanned = false;
                                break;
                            }
                        }

                        if (canBePlanned) {
                            match.setTimestamp(this.fieldList.get(fieldNumber).getFieldEndTime()); // Tiden kampen skal spilles sættes.
                            match.setField(this.fieldList.get(fieldNumber));    // Kampens bane sættes.
                            match.setPlanned(true); // Kampen sættes til at være planlagt.
                            // MatchDay'ens
                            this.fieldList.get(fieldNumber).setFieldEndTime(this.fieldList.get(fieldNumber).getFieldEndTime()
                                    .plusMinutes((match.getDuration() + this.timeBetweenMatches)));
                            outputMatches.add(match); // Kampen tilføjes til outputMatches.
                            matchCounter++; // Når en kamp er tilføjet, tælles matchCounter op.
                        } else {
                            // Hvis kampen ikke kan planlægges, lægges den i tempMatches.
                            tempMatches.add(match);
                        }
                    }
                }
            }
            // alle de kampe der er "Planned" fjernes fra tempMatches.
            // Jeg kan ikke få kampen fjernet fra tempMatches på samme tid med kampen indsættes i outputMatches.
            for (int i = 0; i < tempMatches.size(); i++) {
                if (tempMatches.get(i).isPlanned()) {
                    tempMatches.remove(i);
                }
            }
        } while (!tempMatches.isEmpty());

        this.matches = outputMatches;
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

    private void setFieldsEndTime() {
        for (Field field : this.fieldList) {
            field.setFieldEndTime(this.startTime);
        }
    }

    public String getName() {
        return name;
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
