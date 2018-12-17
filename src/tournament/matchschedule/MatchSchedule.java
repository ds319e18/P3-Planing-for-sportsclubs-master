package tournament.matchschedule;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import tournament.Match;
import tournament.pool.Pool;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class MatchSchedule {
    private ArrayList<MatchDay> matchDays = new ArrayList<>();
    private LocalDate startDate;
    private LocalDate endDate;

    public MatchSchedule(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        createMatchDays();
    }

    private void createMatchDays() {
        for (int iter = 0; iter < getNumberOfMatchDays(); iter++) {
            this.matchDays.add(new MatchDay(startDate.plusDays(iter)));
            this.matchDays.get(iter).setName("Dag " + (iter + 1));
        }
    }

    public void setTimeBetweenMatchDays(int timeBetweenMatchDays) {
        for (MatchDay matchDay : matchDays) {
            matchDay.setTimeBetweenMatches(timeBetweenMatchDays);
        }
    }

    public void setMixedMatches() {
    }

    public void setNoMixedMatches(ArrayList<Pool> poolList) {
        for (MatchDay matchDay : matchDays) {
            ArrayList<Match> outputMatches = new ArrayList<>();
            int fieldNumber = 0;

            for (Field field : matchDay.getFieldList()) {
                field.setFieldEndTime(matchDay.getStartTime());
            }

            for (Pool pool : poolList) {
                ArrayList<Match> matches = new ArrayList<>();
                matches.addAll(pool.getGroupBracket().getMatches());
                matches.addAll(pool.getPlayoffBracket().getMatches());

                for (Match match : matches) {
                    // Tjekker at der ikke tilføjes kampe såldes at tiden overskrider den endtime som er valgt
                    if (fieldNumber < matchDay.getFieldList().size() &&
                            !((matchDay.getFieldList().get(fieldNumber).getFieldEndTime().plusMinutes(match.getDuration()
                                    + matchDay.getTimeBetweenMatches()).isAfter(matchDay.getEndTime())))) {

                        if (!match.isPlanned()) {
                            matchDay.getFieldList().get(fieldNumber).setOccupied(true);
                            match.setTimestamp(matchDay.getFieldList().get(fieldNumber).getFieldEndTime()); // Tiden kampen skal spilles sættes.
                            match.setField(matchDay.getFieldList().get(fieldNumber));    // Kampens bane sættes.
                            match.setDate(matchDay.getDate());
                            match.setPlanned(true); // Kampen sættes til at være planlagt.
                            // MatchDay'ens
                            matchDay.getFieldList().get(fieldNumber).setFieldEndTime(matchDay.getFieldList().get(fieldNumber).getFieldEndTime()
                                    .plusMinutes((match.getDuration() + matchDay.getTimeBetweenMatches())));
                            outputMatches.add(match); // Kampen tilføjes til outputMatches.
                        }

                    }
                }

                if (fieldNumber < matchDay.getFieldList().size() &&
                        matchDay.getFieldList().get(fieldNumber).isOccupied()) {
                    matchDay.getFieldList().get(fieldNumber).setOccupied(false);
                    fieldNumber++;
                }
            }
            matchDay.setMatches(outputMatches);
        }
    }

    public MatchDay findMatchDay(String name) {
        for (MatchDay matchDay : matchDays) {
            if (matchDay.getName().equals(name))
                return matchDay;
        }
        return null;
    }

    @Override
    public String toString() {
        return "kamprogrammet";
    }

    // Getters
    public LocalDate getStartDate() { return startDate; }

    public LocalDate getEndDate() { return endDate; }

    public ArrayList<MatchDay> getMatchDays() {
        return matchDays;
    }

    public long getNumberOfMatchDays() {
        return (DAYS.between(this.startDate, this.endDate) + 1);
    }


}
