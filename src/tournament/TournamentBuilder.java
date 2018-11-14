package tournament;

import tournament.matchschedule.Field;
import tournament.matchschedule.MatchSchedule;
import tournament.pool.Pool;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class TournamentBuilder {
    private String name = "MyTournament";
    private boolean active = false;
    private LocalDate startDate;
    private LocalDate endDate;
    private TournamentType type;
    private ArrayList<Field> fieldList = new ArrayList<>();
    private ArrayList<Pool> poolList = new ArrayList<>();
    private MatchSchedule matchSchedule;

    public TournamentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TournamentBuilder setActive(boolean active) {
        this.active = active;
        return this;
    }

    public TournamentBuilder setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public TournamentBuilder setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public TournamentBuilder setType(TournamentType type) {
        this.type = type;
        return this;
    }

    public TournamentBuilder addToFieldList(Field... fields) {
        ArrayList<Field> temp = new ArrayList<>(Arrays.asList(fields));
        this.fieldList.addAll(temp);
        return this;
    }

    public TournamentBuilder setPoolList(Pool... pools) {
        ArrayList<Pool> temp = new ArrayList<>(Arrays.asList(pools));
        this.poolList.addAll(temp);
        return this;
    }

    public TournamentBuilder setMatchSchedule(MatchSchedule matchSchedule) {
        this.matchSchedule = matchSchedule;
        return this;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public TournamentType getType() {
        return type;
    }

    public ArrayList<Field> getFieldList() {
        return fieldList;
    }

    public ArrayList<Pool> getPoolList() {
        return poolList;
    }

    public MatchSchedule getMatchSchedule() {
        return matchSchedule;
    }
}
