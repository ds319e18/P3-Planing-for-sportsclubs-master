package tournament;

public enum TournamentType {
    GroupAndKnockout("Gruppe- og slutspil");

    private String tournamentTypeName;

    TournamentType(String tournamentTypeName) {
        this.tournamentTypeName = tournamentTypeName;
    }

    @Override
    public String toString() {
        return tournamentTypeName;
    }
}
