package tournament.matchschedule;

import tournament.Match;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class MatchDay {
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime actualEndTime;
    private LocalDate date;
    private int timeBetweenMatches;
    private ArrayList<Match> matches;
    private ArrayList<Field> fieldList;

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public void setMatchesMix(ArrayList<Match> matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return "                   " + name + ": " + startTime + "-" + endTime + " " + date + '\n' +
                matches.toString().replace(",", "").replace("[", " ").replace("]", "");
    }

    public static class Builder {
        private String name;
        private LocalTime startTime;
        private LocalTime endTime;
        private LocalDate date;
        private ArrayList<Field> fieldList;
        private int timeBetweenMatches;
        private ArrayList<Match> matches = new ArrayList<>();
        private LocalTime actualEndTime;


        public Builder(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.actualEndTime = startTime;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder setFieldsToUse(ArrayList<Field> fieldList) {
            this.fieldList = fieldList;
            return this;
        }

        public Builder setTimeBetweenMatches(int timeBetweenMatches) {
            this.timeBetweenMatches = timeBetweenMatches;
            return this;
        }

        public Builder setMatchesNoMix(ArrayList<Match> inputMatches) {
            ArrayList<Match> outputMatches = new ArrayList<>();
            int fieldNumber = 0;
            int matchCounter = 0;

            for (Field field : this.fieldList) {
                field.setFieldEndTime(this.startTime);
            }

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
                //Duration allTimeBetweenMatches = Duration.ofMinutes(this.timeBetweenMatches * matchesPlanned);
                matchCounter++;
                }

            this.matches = outputMatches;
            return this;
        }

        public MatchDay build() {
            MatchDay matchDay = new MatchDay();

            matchDay.name = this.name;
            matchDay.startTime = this.startTime;
            matchDay.endTime = this.endTime;
            matchDay.date = this.date;
            matchDay.timeBetweenMatches = this.timeBetweenMatches;
            matchDay.matches = this.matches;
            matchDay.fieldList = this.fieldList;
            matchDay.actualEndTime = this.actualEndTime;

            return matchDay;
        }
    }
}
