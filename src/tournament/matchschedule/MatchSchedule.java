package tournament.matchschedule;

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

    public void createMatchDays() {
        for (int iter = 0; iter < getNumberOfMatchDays(); iter++) {
            this.matchDays.add(new MatchDay(startDate.plusDays(iter)));
        }
    }
}
