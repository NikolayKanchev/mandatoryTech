package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import logic.FoosballLogic;
import model.Schedule;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/09/2017.
 */
public class ContPlayerMatches implements Initializable
{
    FoosballLogic foosballLogic = FoosballLogic.getInstance();


    UseAgain use = UseAgain.getInstance();

    @FXML
    ChoiceBox exitOptions;

    @FXML
    TableView<Schedule> tableView;

    @FXML
    TableColumn<Integer, Schedule> matchIDColumn;

    @FXML
    TableColumn<Date, Schedule> dateColumn;

    @FXML
    TableColumn<String, Schedule> team1Column, team2Column, resultColumn, stageColumn;

    @FXML
    ComboBox tournamentComboBox;

    //Listener loads the data again if tournamentComboBox value is modified
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));

        ObservableList<String> tourNames = FXCollections.observableArrayList();

        tourNames.addAll( new ArrayList<>(foosballLogic.getPlayerTournamentsNames()));

        tournamentComboBox.setItems(tourNames);

        tournamentComboBox.getSelectionModel().selectFirst();

        loadData();


        tournamentComboBox.valueProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                loadData();
            }
        });
    }

    private void loadData()
    {
        matchIDColumn.setCellValueFactory(new PropertyValueFactory<>("matchID"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        team1Column.setCellValueFactory(new PropertyValueFactory<>("team1ID"));
        team2Column.setCellValueFactory(new PropertyValueFactory<>("team2ID"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        stageColumn.setCellValueFactory(new PropertyValueFactory<>("stage"));


        ArrayList<Schedule> playerSchedule = new ArrayList<>();
        playerSchedule.addAll(foosballLogic.getSchedule(tournamentComboBox.getSelectionModel().getSelectedItem().toString(), "First Round"));
        playerSchedule.addAll(foosballLogic.getSchedule(tournamentComboBox.getSelectionModel().getSelectedItem().toString(), "Semifinal"));
        playerSchedule.addAll(foosballLogic.getSchedule(tournamentComboBox.getSelectionModel().getSelectedItem().toString(), "Final"));

        ObservableList<Schedule> observableSchedule = FXCollections.observableArrayList();
        observableSchedule.addAll(playerSchedule);
        tableView.setItems(observableSchedule);
    }

    public void exitOrLogOut(MouseEvent mouseEvent)
    {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void goBack(ActionEvent actionEvent) throws IOException
    {
        use.goBack(actionEvent, "screenPlayerChoice.fxml");
    }
}
