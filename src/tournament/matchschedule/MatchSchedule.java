package tournament.matchschedule;

import tournament.Match;

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

    public long getNumberOfMatchDays() {
        return (DAYS.between(this.startDate, this.endDate) + 1);
    }

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

    public void setNoMixedMatches(ArrayList<Match> matches) {
        for (MatchDay matchDay : matchDays) {
            matchDay.setMatchesNoMix(matches);
        }
    }

    public MatchDay findMatchDay(String name) {
        for (MatchDay matchDay : matchDays) {
            if (matchDay.getName().equals(name))
                return matchDay;
        }
        return null;
    }
}
