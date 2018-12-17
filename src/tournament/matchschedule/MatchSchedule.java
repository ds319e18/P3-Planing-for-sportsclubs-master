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

    public ObservableValue<ComboBox> getMatchDaysAsComboBox() {
        ComboBox<MatchDay> matchDayComboBox = new ComboBox<>();
        matchDayComboBox.getItems().addAll(matchDays);
        ObservableValue<ComboBox> comboBoxObservableValue = new SimpleObjectProperty<>(matchDayComboBox);
        return comboBoxObservableValue;
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
            matchDay.setNoMixedMatches(poolList);
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
