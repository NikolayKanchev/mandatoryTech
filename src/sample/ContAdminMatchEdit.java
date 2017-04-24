package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/14/2017.
 */
public class ContAdminMatchEdit implements Initializable{
    private FoosballLogic foosballLogic = FoosballLogic.getInstance();
    private Match matchToEdit = foosballLogic.getChosenMatch();
    private UseAgain use = UseAgain.getInstance();

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
    public void initialize(URL location, ResourceBundle resources) {
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
        redLabel.setVisible(false);
        loadData();
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
    }

    public void saveMatchChanges(ActionEvent actionEvent) throws IOException
    {
        int t1scores = 0;
        int t2scores = 0;
        String tournament = "";
        String team1 = "";
        String team2 = "";

        redLabel.setVisible(false);


        if(tourComboBox.getSelectionModel().isEmpty())
        {
            tournament = tourComboBox.getPromptText();
        }else
        {
            tournament = tourComboBox.getSelectionModel().getSelectedItem().toString();
        }

        if(team1ComboBox.getSelectionModel().isEmpty()){
            team1 = team1ComboBox.getPromptText();
        }else
        {
            team1 = team1ComboBox.getSelectionModel().getSelectedItem().toString();
        }

        if(team2ComboBox.getSelectionModel().isEmpty()){
            team2 = team2ComboBox.getPromptText();
        }else
        {
            team2 = team2ComboBox.getSelectionModel().getSelectedItem().toString();
        }

        if (date.getValue() == null ||
                tournament == null ||
                stage.getText().isEmpty() ||
                team1 == null ||
                team2 == null ||
                team1scores.getText().isEmpty() ||
                team2scores.getText().isEmpty())
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
                date.getValue(), tournament,
                stage.getText(), team1,
                team2, t1scores, t2scores);

        }catch (Exception e){
            redLabel.setText("Enter a whole number for 'Team 1 scores' and 'Team 2 scores'");
            redLabel.setVisible(true);
            return;
        }

        use.goBack(actionEvent, "screenAdminMatches.fxml");
    }

    public void chooseTournament(ActionEvent actionEvent)
    {

        //tourComboBox.setItems(foosballLogic.getTournamentsNames());
    }

    public void chooseTeam1(ActionEvent actionEvent)
    {

    }

    public void chooseTeam2(ActionEvent actionEvent)
    {

    }
}
