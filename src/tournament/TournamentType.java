package tournament;

public enum TournamentType {
    Group("Only Group"), Knockout("Only KnockoutPlay"), GroupAndKnockout("Group and KnockoutPlay");

    private String tournamentTypeName;

    TournamentType(String tournamentTypeName) {
        this.tournamentTypeName = tournamentTypeName;
    }

    @Override
    public String toString() {
        return tournamentTypeName;
    }
}
