package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import logic.FoosballLogic;
import model.Match;
import model.Team;
import model.Tournament;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/09/2017.
 */
public class ContAdminMatches implements Initializable{
    FoosballLogic foosballLogic = FoosballLogic.getInstance();
    UseAgain use = UseAgain.getInstance();
    ArrayList<Match> matches;
    ArrayList<Team> teams;
    ArrayList<Tournament> tournaments;



    @FXML
    ChoiceBox exitOptions;

    @FXML
    TableView<Match> tableView;

    @FXML
    TableColumn<Integer, Match> idColumn, team1scoresColumn, team2scoresColumn;

    @FXML
    TableColumn<String, Match> tournamentColumn, stageColumn, team1Column, team2Column;

    @FXML
    TableColumn<Date, Match> dateColumn;

    @FXML
    DatePicker startDate;

    @FXML
    Label redLabel, redLabelTop;

    @FXML
    ComboBox tournamentComboBox, team1ComboBox, team2ComboBox;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
        loadData();

        startDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if (startDate.getValue().isBefore(LocalDate.now())) {
                    startDate.setValue(LocalDate.now());
                    redLabel.setText("The match can not start from previous date");
                    redLabel.setVisible(true);
                }

                if (startDate.getValue().isAfter(LocalDate.now())) {
                    redLabel.setVisible(false);
                }
            }
        });
    }

    private void loadData()
    {
        matches = foosballLogic.getMatches();
        teams = foosballLogic.getTeams();
        tournaments = foosballLogic.getTournaments();
        foosballLogic.setTeamNames(matches, teams);
        foosballLogic.setTournamentsNames(matches, tournaments);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        tournamentColumn.setCellValueFactory(new PropertyValueFactory<>("tournamentName"));
        stageColumn.setCellValueFactory(new PropertyValueFactory<>("stage"));
        team1Column.setCellValueFactory(new PropertyValueFactory<>("team1Name"));
        team2Column.setCellValueFactory(new PropertyValueFactory<>("team2Name"));
        team1scoresColumn.setCellValueFactory(new PropertyValueFactory<>("team1scores"));
        team2scoresColumn.setCellValueFactory(new PropertyValueFactory<>("team2scores"));

        ObservableList<Match> observableMatches = FXCollections.observableArrayList();
        observableMatches.addAll(matches);
        tableView.setItems(observableMatches);

    }

    public void exitOrLogOut(MouseEvent mouseEvent) {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
       use.goBack(actionEvent, "screenAdminChoice.fxml");
    }

    public void addNewMatch(ActionEvent actionEvent) {
        if(startDate.getValue() == null ||
                tournamentComboBox.getSelectionModel().isEmpty() ||
                team1ComboBox.getSelectionModel().isEmpty() ||
                team2ComboBox.getSelectionModel().isEmpty()){
            redLabel.setText("You have fill out everything");
            redLabel.setVisible(true);
            return;
        }else {

            redLabel.setVisible(false);

            int tournamentID = 0;
            int team1ID = 0;
            int team2ID = 0;

            String selectedTeam1 = team1ComboBox.getSelectionModel().getSelectedItem().toString();
            String selectedTeam2 = team2ComboBox.getSelectionModel().getSelectedItem().toString();
            String selectedTournament = tournamentComboBox.getSelectionModel().getSelectedItem().toString();

            for (Tournament tournament: tournaments)
            {
                if(tournament.getName().equals(selectedTournament))
                {
                    tournamentID = tournament.getId();
                }
            }

            for (Team team: teams)
            {
                if(team.getName().equals(selectedTeam1))
                {
                    team1ID = team.getId();
                }

                if(team.getName().equals(selectedTeam2))
                {
                    team2ID = team.getId();
                }
            }

            foosballLogic.addNewMatch(startDate.getValue(), tournamentID, team1ID, team2ID);
            loadData();
        }
    }

    public void deleteMatch(ActionEvent actionEvent) {
        Match selectedMatch = tableView.getSelectionModel().getSelectedItem();
        if(selectedMatch == null){
            redLabelTop.setVisible(true);
            return;
        }

        redLabelTop.setVisible(false);

        foosballLogic.deleteMatch(selectedMatch.getId());
        loadData();
    }

    public void editMatch(ActionEvent actionEvent) {

    }

    public void chooseDate(ActionEvent actionEvent)
    {

    }

    public void chooseTeam1(MouseEvent mouseEvent)
    {
        ObservableList<String> t = FXCollections.observableArrayList();

        for(Team team: teams)
        {
            t.add(team.getName());
        }

        team1ComboBox.setItems(t);
    }

    public void chooseTeam2(MouseEvent mouseEvent)
    {
        if(team1ComboBox.getSelectionModel().isEmpty())
        {
            redLabel.setText("Select first 'Team1'");
            redLabel.setVisible(true);
            return;
        }else
        {
            redLabel.setVisible(false);
            ObservableList<String> items = FXCollections.observableArrayList();

            for(Team team: teams)
            {
                if(!team.getName().equals(team1ComboBox.getSelectionModel().getSelectedItem().toString())){
                    items.add(team.getName());
                }
            }

            team2ComboBox.setItems(items);
        }
    }

    public void chooseTournament(MouseEvent mouseEvent)
    {
        ObservableList<String> items = FXCollections.observableArrayList();

        for(Tournament tournament: tournaments){
            items.add(tournament.getName());
        }

        tournamentComboBox.setItems(items);
    }
}
