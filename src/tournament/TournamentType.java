package tournament;

public enum TournamentType {
    Group("Only Group"), Knockout("Only Knockout"), GroupAndKnockout("Group and Knockout");

    private String tournamentTypeName;

    TournamentType(String tournamentTypeName) {
        this.tournamentTypeName = tournamentTypeName;
    }

    @Override
    public String toString() {
        return tournamentTypeName;
    }
}
