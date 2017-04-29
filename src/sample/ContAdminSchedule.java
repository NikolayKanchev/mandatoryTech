package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.FoosballLogic;
import model.Match;
import model.Schedule;
import model.Tournament;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/09/2017.
 */
public class ContAdminSchedule implements Initializable
{
    FoosballLogic foosballLogic = FoosballLogic.getInstance();
    UseAgain use = UseAgain.getInstance();
    ArrayList<Schedule> scheduleFR = new ArrayList<>();
    ArrayList<Schedule> scheduleSemi = new ArrayList<>();
    ArrayList<Schedule> scheduleFinal = new ArrayList<>();
    String chosenTournament;

    @FXML
    ChoiceBox exitOptions;

    @FXML
    ComboBox tournamentComboBox;

    @FXML
    TableView<Schedule> tableView, tableViewSemi, tableViewFinal;

    @FXML
    TableColumn<Date, Schedule> dateColumn, dateSemiColumn, dateFinalColumn;

    @FXML
    TableColumn<String, Schedule> resultColumn, resultSemiColumn, resultFinalColumn, team1Column, team1SemiColumn, team1FinalColumn;

    @FXML
    TableColumn<String, Schedule> team2Column, team2SemiColumn, team2FinalColumn;

    @FXML
    TableColumn<Integer, Schedule> matchID, matchIDSemiColumn, matchIDFinalColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        loadTournaments();
        loadSchedule();
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));

        tournamentComboBox.valueProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                chosenTournament = tournamentComboBox.getSelectionModel().getSelectedItem().toString();
                loadSchedule();
            }
        });
    }



    public void exitOrLogOut(MouseEvent mouseEvent)
    {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void goBack(ActionEvent actionEvent) throws IOException
    {
        use.goBack(actionEvent, "screenAdminChoice.fxml");
    }

    private void loadSchedule()
    {
        chosenTournament = tournamentComboBox.getSelectionModel().getSelectedItem().toString();
        scheduleFR = foosballLogic.getSchedule(chosenTournament, "First Round");
        scheduleSemi = foosballLogic.getSchedule(chosenTournament, "Semifinal");
        scheduleFinal = foosballLogic.getSchedule(chosenTournament, "Final");

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateSemiColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateFinalColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        matchID.setCellValueFactory(new PropertyValueFactory<>("matchID"));
        matchIDSemiColumn.setCellValueFactory(new PropertyValueFactory<>("matchID"));
        matchIDFinalColumn.setCellValueFactory(new PropertyValueFactory<>("matchID"));

        team1Column.setCellValueFactory(new PropertyValueFactory<>("team1ID"));
        team1SemiColumn.setCellValueFactory(new PropertyValueFactory<>("team1ID"));
        team1FinalColumn.setCellValueFactory(new PropertyValueFactory<>("team1ID"));

        team2Column.setCellValueFactory(new PropertyValueFactory<>("team2ID"));
        team2SemiColumn.setCellValueFactory(new PropertyValueFactory<>("team2ID"));
        team2FinalColumn.setCellValueFactory(new PropertyValueFactory<>("team2ID"));

        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        resultSemiColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        resultFinalColumn.setCellValueFactory(new PropertyValueFactory<>("result"));

        ObservableList<Schedule> first = FXCollections.observableArrayList();
        ObservableList<Schedule> semi = FXCollections.observableArrayList();
        ObservableList<Schedule> fin = FXCollections.observableArrayList();
        first.addAll(scheduleFR);
        semi.addAll(scheduleSemi);
        fin.addAll(scheduleFinal);

        tableView.setItems(first);
        tableViewSemi.setItems(semi);
        tableViewFinal.setItems(fin);
    }

    private void loadTournaments()
    {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(foosballLogic.getTournamentsNames());
        tournamentComboBox.setItems(items);
        tournamentComboBox.getSelectionModel().selectFirst();
    }
}
