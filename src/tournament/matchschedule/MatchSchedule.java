package tournament.matchschedule;

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

    // Getters
    public long getNumberOfMatchDays() {
        return (DAYS.between(this.startDate, this.endDate) + 1);
    }

    public LocalDate getStartDate() { return startDate; }

    public LocalDate getEndDate() { return endDate; }

    public ArrayList<MatchDay> getMatchDays() {
        return matchDays;
    }

    private void createMatchDays() {
        for (int iter = 0; iter < getNumberOfMatchDays(); iter++) {
            this.matchDays.add(new MatchDay(startDate.plusDays(iter)));
            this.matchDays.get(iter).setName("Day " + (iter + 1));
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
}
