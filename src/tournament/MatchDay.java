package tournament;

import tournament.matchschedule.Field;
import tournament.matchschedule.MatchSchedule;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class MatchDay {
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime actualEndTime;
    private LocalDate date;
    private int timeBetweenMatches;
    private ArrayList<Match> matches;
    private ArrayList<Field> fieldList;

    public void setMatchesNoMix(ArrayList<Match> inputMatches) {
        Collections.sort(inputMatches);
        int matchesPlanned = 0;

        for (Match match : inputMatches) {
            this.actualEndTime.plusMinutes(match.getDuration() + timeBetweenMatches);
            if (actualEndTime.isBefore(endTime) || actualEndTime.equals(endTime)) {
                for (Field field : this.fieldList) {
                    if (!field.isOccupied()) {
                        if (matchesPlanned == 0) {
                            match.setTimestamp(startTime.plusMinutes((match.getDuration() * matchesPlanned)));
                            match.setField(field);
                        } else {
                            match.setTimestamp(startTime.plusMinutes((match.getDuration() * matchesPlanned) + timeBetweenMatches));
                            match.setField(field);
                        }
                    }
                }
                Duration allTimeBetweenMatches = Duration.ofMinutes(timeBetweenMatches * matchesPlanned);
                matchesPlanned++;
                actualEndTime.plusMinutes((match.getDuration() * matchesPlanned) + allTimeBetweenMatches.toMinutes());
            }
        }
    }

    public void setMatchesMix(ArrayList<Match> matches) {


        this.matches = matches;
    }

    public static class Builder {
        private LocalTime startTime;
        private LocalTime endTime;
        private LocalDate date;
        private ArrayList<Field> fieldList;
        private int timeBetweenMatches;
        private ArrayList<Match> matches = new ArrayList<>();

        private LocalDate actualEndTime;


        public Builder(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
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

        public MatchDay build() {
            MatchDay matchDay = new MatchDay();

            matchDay.startTime = this.startTime;
            matchDay.endTime = this.endTime;
            matchDay.date = this.date;
            matchDay.timeBetweenMatches = this.timeBetweenMatches;
            matchDay.matches = this.matches;
            matchDay.fieldList = this.fieldList;
            matchDay.actualEndTime = this.startTime;

            return matchDay;
        }
    }
}
