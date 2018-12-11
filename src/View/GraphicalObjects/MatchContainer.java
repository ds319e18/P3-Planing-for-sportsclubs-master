package View.GraphicalObjects;

import controller.CreatingMatchScheduleController;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import tournament.Match;

import java.time.LocalTime;
import java.util.Objects;

public class MatchContainer extends  HBox{
    private Match match;
    private boolean selected;
    private Text timeIntervalText;

    //Creates an empty matchContainer with a startTime in the gridPane
    public MatchContainer(LocalTime matchStartTime) {
        Text matchNameText = new Text("Kamp --");
        timeIntervalText = new Text(matchStartTime.toString());
        Text guidingText = new Text("Tilføj kamp her");
        setStyleOfText(matchNameText);
        setStyleOfText(timeIntervalText);
        guidingText.setStyle("-fx-font-size: 18");

        VBox box1 = new VBox(matchNameText, timeIntervalText);
        HBox box2 = new HBox(guidingText);
        box1.setStyle("-fx-border-color: BLACK;");

        this.getChildren().addAll(box1, box2);
        this.setMaxWidth(270);
        this.setStyle("-fx-border-color: BLACK;");
        this.setSelected(false);
    }

    public MatchContainer(Match match) {
        this.match = match;

        Text matchNameText = new Text(match.getName());
        timeIntervalText = new Text(String.valueOf(match.getDuration()) + " minutter");
        Text firstTeamText = new Text(match.getFirstTeam().getName());
        Text secondTeamText = new Text(match.getSecondTeam().getName());
        Text poolText = new Text("U" + match.getFirstTeam().getYearGroup() + " - " + match.getFirstTeam().getSkillLevel());
        Text fieldText = new Text("Bane: --");

        setStyleOfText(matchNameText);
        setStyleOfText(timeIntervalText);
        setStyleOfText(firstTeamText);
        setStyleOfText(secondTeamText);
        setStyleOfText(poolText);
        setStyleOfText(fieldText);

        VBox box1 = new VBox(matchNameText, timeIntervalText);
        VBox box2 = new VBox(firstTeamText, secondTeamText);
        VBox box3 = new VBox(poolText, fieldText);
        this.getChildren().addAll(box1, box2, box3);
        this.setStyle("-fx-border-color: BLACK;");
        box1.setStyle("-fx-border-color: BLACK;");
        this.setSelected(false);
    }

    // Nyt
    public MatchContainer(Match match, boolean bool) {
        this.match = match;

        Text matchNameText = new Text(match.getName());
        timeIntervalText = new Text(match.getTimeStamp() + " - " + match.getTimeStamp().plusMinutes(match.getDuration()));
        Text firstTeamText = new Text(match.getFirstTeam().getName());
        Text secondTeamText = new Text(match.getSecondTeam().getName());
        Text firstTeamResultText = new Text((match.getResult() == null ? "-" : Integer.toString(match.getResult().getFirstTeamScore())));
        Text secondTeamResultText = new Text((match.getResult() == null ? "-" : Integer.toString(match.getResult().getSecondTeamScore())));
        Text fieldText = new Text(match.getField().getName());

        String groupNumber = (match.getFirstTeam().getGroupNumber() == match.getSecondTeam().getGroupNumber() && match.getFirstTeam().getGroupNumber() > 0 && match.getName().equals("Group Match:\t")
                ? " G" + match.getFirstTeam().getGroupNumber() : "");
        Text poolText = new Text("U" + match.getFirstTeam().getYearGroup() + " - " + match.getFirstTeam().getSkillLevel() + groupNumber);

        setStyleOfText(matchNameText);
        setStyleOfText(timeIntervalText);
        setStyleOfText(firstTeamText);
        setStyleOfText(firstTeamResultText);
        setStyleOfText(secondTeamResultText);
        setStyleOfText(secondTeamText);
        setStyleOfText(poolText);
        setStyleOfText(fieldText);

        VBox box1 = new VBox(matchNameText, timeIntervalText);
        VBox box2 = new VBox(firstTeamText, secondTeamText);
        VBox box3 = new VBox(firstTeamResultText, secondTeamResultText);
        VBox box4 = new VBox(poolText, fieldText);
        this.getChildren().addAll(box1, box2, box3, box4);
        this.setStyle("-fx-border-color: BLACK;");
        box1.setStyle("-fx-border-color: BLACK;");
        this.setSelected(false);
    }

    public MatchContainer(Match match, MatchContainer matchContainer) {
        this.match = match;
        LocalTime matchStartTime = LocalTime.parse(matchContainer.getTimeIntervalText().getText().substring(0,5));

        Text matchNameText = new Text(match.getName());
        timeIntervalText = new Text(matchStartTime + " - " + matchStartTime.plusMinutes(this.match.getDuration()));
        Text firstTeamText = new Text(match.getFirstTeam().getName());
        Text secondTeamText = new Text(match.getSecondTeam().getName());
        Text poolText = new Text("U" + match.getFirstTeam().getYearGroup() + " - " + match.getFirstTeam().getSkillLevel());
        Text fieldText = new Text("Bane: " + String.valueOf(GridPane.getColumnIndex(matchContainer) +1));

        setStyleOfText(matchNameText);
        setStyleOfText(timeIntervalText);
        setStyleOfText(firstTeamText);
        setStyleOfText(secondTeamText);
        setStyleOfText(poolText);
        setStyleOfText(fieldText);

        VBox box1 = new VBox(matchNameText, timeIntervalText);
        VBox box2 = new VBox(firstTeamText, secondTeamText);
        VBox box3 = new VBox(poolText, fieldText);
        this.getChildren().addAll(box1, box2, box3);
        this.setStyle("-fx-border-color: BLACK;");
        box1.setStyle("-fx-border-color: BLACK;");
        this.setSelected(false);
        this.match.setTimestamp(matchStartTime);
    }

    /*
    public void moveOneRowUpInGridPane(GridPane gridPane, int timeBetweenMatches) {
        gridPane.getChildren().remove(this);
        gridPane.add(this, GridPane.getColumnIndex(this), GridPane.getRowIndex(this) -1);
        MatchContainer matchContainer = CreatingMatchScheduleController.
                getMatchContainerFromGridPane(GridPane.getColumnIndex(this),
                        GridPane.getRowIndex(this) -2, gridPane);

        this.setStartTimeFromPreviousMatchContainer(matchContainer,
                timeBetweenMatches);
    }

    private void setStartTimeFromPreviousMatchContainer(MatchContainer previousContainer, int timeBetweenMatches) {
        GridPane gridPane = (GridPane) previousContainer.getParent();
        if (!previousContainer.hasMatch() && !this.hasMatch())
            gridPane.getChildren().remove(this);
        LocalTime newMatchStartTime = previousContainer.getMatchEndTime().plusMinutes(timeBetweenMatches);
        VBox box = (VBox) this.getChildren().get(0);
        box.getChildren().remove(timeIntervalText);
        this.timeIntervalText =
                new Text(newMatchStartTime + " - " + newMatchStartTime.plusMinutes(this.getMatch().getDuration()));
        box.getChildren().add(timeIntervalText);
    } */

    private void setStyleOfText(Text text) {
        text.setWrappingWidth(80);
        text.setTextAlignment(TextAlignment.CENTER);
    }

    public Match getMatch() {
        return match;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected)
            this.setStyle("-fx-background-color: #A9A9A9");
        if (!selected) {
            this.setStyle("-fx-background-color: #FFFFFF");
            this.setStyle("-fx-border-color: BLACK;");
        }
    }

    public Text getTimeIntervalText() {
        return timeIntervalText;
    }

    public LocalTime getMatchEndTime() {
        return this.match.getTimeStamp().plusMinutes(match.getDuration());
    }

    public boolean fitsForMatch(Match match, int timeBetweenMatches) {

        //check if there exits a matchContainer after this matchContainer
        MatchContainer nextMatchContainer = getNextMatchContainerInGridPane();
        if (nextMatchContainer != null) {
            LocalTime currentMatchStartTime = LocalTime.parse(this.timeIntervalText.getText().substring(0, 5));
            LocalTime currentMatchEndTime = currentMatchStartTime.plusMinutes(match.getDuration());
            LocalTime nextMatchStartTime = LocalTime.parse(nextMatchContainer.getTimeIntervalText().
                    getText().substring(0, 5));
            if (!currentMatchEndTime.plusMinutes(timeBetweenMatches).isAfter(nextMatchStartTime))
                return true;
            else
                return false;
        } else
            return true;
    }

    public MatchContainer getNextMatchContainerInGridPane() {
        GridPane gridPane = (GridPane) this.getParent();
        return CreatingMatchScheduleController.getMatchContainerFromGridPane
                (GridPane.getColumnIndex(this), GridPane.getRowIndex(this) + 1, gridPane);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchContainer that = (MatchContainer) o;
        return isSelected() == that.isSelected() &&
                Objects.equals(getMatch(), that.getMatch()) &&
                Objects.equals(getTimeIntervalText(), that.getTimeIntervalText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMatch(), isSelected(), getTimeIntervalText());
    }

    public boolean hasMatch() {
        if (this.match == null)
            return false;
        else
            return true;
    }
}
