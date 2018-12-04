package tournament.matchschedule.GraphicalObjects;

import javafx.scene.Parent;
import javafx.scene.control.ListView;
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

    //Creates an empty matchContainer with a startTime
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
        Text poolText = new Text(match.getName());
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

    public MatchContainer(Match match, MatchContainer matchContainer) {
        this.match = match;
        LocalTime matchStartTime = LocalTime.parse(matchContainer.getTimeIntervalText().getText().substring(0,5));

        Text matchNameText = new Text(match.getName());
        timeIntervalText = new Text(matchStartTime + " - " + matchStartTime.plusMinutes(this.match.getDuration()));
        Text firstTeamText = new Text(match.getFirstTeam().getName());
        Text secondTeamText = new Text(match.getSecondTeam().getName());
        Text poolText = new Text(match.getName());
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


    private void handleSelection() {
        //Checks whether the matchContainer belongs to a GridPane or a ListView
        if (this.getParent() instanceof GridPane)
            handleSelectionInGridPane();
        else if (this.getParent() instanceof ListView)
            handleSelectionInListView();


    }

    private void handleSelectionInGridPane() {
        GridPane gridPane = (GridPane) this.getParent();

        selected = true;
    }

    private void handleSelectionInListView() {
        ListView listView = (ListView) this.getParent();

        selected = true;
    }

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
}
