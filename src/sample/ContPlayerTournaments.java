package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import logic.FoosballLogic;
import model.Tournament;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/09/2017.
 */
public class ContPlayerTournaments implements Initializable
{
    FoosballLogic foosballLogic = FoosballLogic.getInstance();

    UseAgain use = UseAgain.getInstance();

    @FXML
    ChoiceBox exitOptions;

    @FXML
    TableColumn<Date,Tournament> startDateColumn, endDateColumn;

    @FXML
    TableColumn<Integer, Tournament> idColumn;

    @FXML
    TableColumn<String, Tournament> nameColumn;

    @FXML
    TableView<Tournament> tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));

        loadTournaments();
    }

    private void loadTournaments()
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        ObservableList<Tournament> tournaments = FXCollections.observableArrayList();
        tournaments.addAll(foosballLogic.getTournaments());
        tableView.setItems(tournaments);
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
