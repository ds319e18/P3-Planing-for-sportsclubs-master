package tournament.matchschedule;

import tournament.Match;
import tournament.MatchDay;
import tournament.pool.Pool;

import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class MatchSchedule {
    private ArrayList<MatchDay> matchDays = new ArrayList<>();
    private LocalDate startDate;
    private LocalDate endDate;

    public MatchSchedule(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private long getNumberOfMatchDays() {
        return (DAYS.between(this.startDate, this.endDate) + 1);
    }




    private void updateMatchSchedule() {
    }
}
