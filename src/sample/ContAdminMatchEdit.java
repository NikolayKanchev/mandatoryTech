package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import logic.FoosballLogic;
import model.Match;
import model.Tournament;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/14/2017.
 */
public class ContAdminMatchEdit implements Initializable{
    private FoosballLogic foosballLogic = FoosballLogic.getInstance();
    private Match matchToEdit = foosballLogic.getChosenMatch();
    private UseAgain use = UseAgain.getInstance();
    private ArrayList<Tournament> tournaments = new ArrayList<>();
    private LocalDate startDate;
    private LocalDate endDate;
    private int team1OldScores;
    private int team2OldScores;

    @FXML
    ChoiceBox exitOptions;

    @FXML
    TextField stage, team1scores, team2scores;

    @FXML
    DatePicker date;

    @FXML
    Button saveButton;

    @FXML
    Label redLabel;

    @FXML
    ComboBox tourComboBox, team1ComboBox, team2ComboBox;

    public void exitOrLogOut(MouseEvent mouseEvent) {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        use.goBack(actionEvent, "screenAdminMatches.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
        redLabel.setVisible(false);
        loadData();

        tourComboBox.valueProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                tournaments = foosballLogic.getTournaments();

                for (Tournament tournament: tournaments)
                {
                    if(tourComboBox.getSelectionModel().getSelectedItem().toString().equals(tournament.getName()))
                    {
                        startDate = tournament.getStartDate().toLocalDate();
                        endDate = tournament.getEndDate().toLocalDate();
                        date.setValue(startDate);
                    }
                }
            }
        });

        date.valueProperty().addListener(new ChangeListener<LocalDate>()
        {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue)
            {
                LocalDate selectedDate = date.getValue();
                if(selectedDate.isBefore(startDate) || selectedDate.isAfter(endDate)){
                    redLabel.setText("The date has to be between: "+ startDate + " and: " + endDate);
                    redLabel.setVisible(true);
                }
                else
                {
                    redLabel.setVisible(false);
                }
            }
        });
    }

    public void loadData(){

        String tournamentName = foosballLogic.getTournamentName(matchToEdit.getTournamentID());
        String team1Name = foosballLogic.getTeamName(matchToEdit.getTeam1ID());
        String team2Name = foosballLogic.getTeamName(matchToEdit.getTeam2ID());

        date.setValue(matchToEdit.getDate().toLocalDate());
        tourComboBox.setPromptText(tournamentName);
        stage.setText(matchToEdit.getStage());
        team1ComboBox.setPromptText(team1Name);
        team2ComboBox.setPromptText(team2Name);
        team1scores.setText(String.valueOf(matchToEdit.getTeam1scores()));
        team2scores.setText(String.valueOf(matchToEdit.getTeam2scores()));
        team1OldScores = matchToEdit.getTeam1scores();
        team2OldScores = matchToEdit.getTeam2scores();

    }

    public void saveMatchChanges(ActionEvent actionEvent) throws IOException
    {
        int t1scores = 0;
        int t2scores = 0;
        String tourName = "";
        String team1Name = "";
        String team2Name = "";

        redLabel.setVisible(false);


        if(tourComboBox.getSelectionModel().isEmpty())
        {
            tourName = tourComboBox.getPromptText();
        }else
        {
            tourName = tourComboBox.getSelectionModel().getSelectedItem().toString();
        }

        if(team1ComboBox.getSelectionModel().isEmpty()){
            team1Name = team1ComboBox.getPromptText();
        }else
        {
            team1Name = team1ComboBox.getSelectionModel().getSelectedItem().toString();
        }

        if(team2ComboBox.getSelectionModel().isEmpty()){
            team2Name = team2ComboBox.getPromptText();
        }else
        {
            team2Name = team2ComboBox.getSelectionModel().getSelectedItem().toString();
        }

        if(team1Name.equals(team2Name)){
            team2ComboBox.setPromptText("");
            redLabel.setText("Choose 'Team 2'");
            redLabel.setVisible(true);
            return;
        }

        if (date.getValue() == null || stage.getText().isEmpty() || team2Name.equals(null))
        {
            redLabel.setText("You have to fill out all the fields");
            redLabel.setVisible(true);
            return;
        }

        try
        {
            t1scores = Integer.parseInt(team1scores.getText());
            t2scores = Integer.parseInt(team2scores.getText());
            foosballLogic.saveMatchChanges(
                date.getValue(), tourName,
                stage.getText(), team1Name,
                team2Name, t1scores, t2scores);

        }catch (Exception e){
            redLabel.setText("Enter a whole number for 'Team 1 scores' and 'Team 2 scores'");
            redLabel.setVisible(true);
            return;
        }

        t1scores = t1scores - team1OldScores;
        t2scores = t2scores - team2OldScores;

        foosballLogic.setPlayerRank(team1Name, team2Name, t1scores, t2scores);

        if (team1OldScores == 0 && team2OldScores == 0)
        {
            foosballLogic.setTeamsWonMatches(team1Name, team2Name, t1scores, t2scores);
            if(t1scores > t2scores)
            {
                int scoresDiff = t1scores - t2scores;
                foosballLogic.addToWinners(matchToEdit.getTournamentID(), matchToEdit.getStage(), matchToEdit.getTeam1ID(), scoresDiff);
            }

            if(t1scores < t2scores)
            {
                int scoresDiff = t2scores - t1scores;
                foosballLogic.addToWinners(matchToEdit.getTournamentID(), matchToEdit.getStage(), matchToEdit.getTeam2ID(), scoresDiff);
            }
        }



        if(team1OldScores > team2OldScores && t1scores < t2scores)
        {
            int oldScoreDiff = team1OldScores - team2OldScores;
            int newScoresDiff = oldScoreDiff - (t2scores - t1scores);
            if(newScoresDiff < 0)
            {
                newScoresDiff = newScoresDiff*(-1);
            }
            foosballLogic.setNewValueTeamsWonMatches(team1Name, team2Name, 1, 2);
            foosballLogic.updateWinners(matchToEdit.getTournamentID(), matchToEdit.getStage(), matchToEdit.getTeam2ID(), newScoresDiff,
                    matchToEdit.getTeam1ID(), oldScoreDiff);

            //foosballLogic.checkAreNextMatchesCreated(matchToEdit.getTournamentID(), matchToEdit.getStage())

        }

        if(team1OldScores < team2OldScores && t1scores > t2scores)
        {
            int oldScoreDiff = team2OldScores - team1OldScores;
            int newScoresDiff = (t1scores - t2scores) - oldScoreDiff;
            if(newScoresDiff < 0)
            {
                newScoresDiff = newScoresDiff*(-1);
            }
            foosballLogic.setNewValueTeamsWonMatches(team1Name, team2Name, 2, 1);
            foosballLogic.updateWinners(matchToEdit.getTournamentID(), matchToEdit.getStage(), matchToEdit.getTeam1ID(), newScoresDiff,
                    matchToEdit.getTeam2ID(), oldScoreDiff);
        }

        foosballLogic.checkPlayedMatches(matchToEdit.getTournamentID());

        use.goBack(actionEvent, "screenAdminMatches.fxml");
    }

    public void chooseTournament(MouseEvent mouseEvent)
    {
        tourComboBox.setItems(foosballLogic.getTournamentsNames());
    }

    public void chooseTeam1(MouseEvent mouseEvent)
    {
        team1ComboBox.setItems(foosballLogic.getTeamsNames());
    }

    public void chooseTeam2(MouseEvent mouseEvent)
    {
        String selectedTeam = "";
        if(team1ComboBox.getSelectionModel().isEmpty())
        {
            selectedTeam = team1ComboBox.getPromptText();
        }else
        {
            selectedTeam = team1ComboBox.getSelectionModel().getSelectedItem().toString();
        }

        team2ComboBox.setItems(foosballLogic.getTeamsNames(selectedTeam));

    }
}
