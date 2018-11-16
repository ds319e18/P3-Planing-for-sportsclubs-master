package tournament;

public enum TournamentType {
    Group("Kun gruppespil"), Knockout("Kun slutspil"), GroupAndKnockout("Gruppe- og slutspil");

    private String tournamentTypeName;

    TournamentType(String tournamentTypeName) {
        this.tournamentTypeName = tournamentTypeName;
    }

    @Override
    public String toString() {
        return tournamentTypeName;
    }
}
