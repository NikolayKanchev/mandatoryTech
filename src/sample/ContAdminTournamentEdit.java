package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.FoosballHouse;
import model.Tournament;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/14/2017.
 */
public class ContAdminTournamentEdit implements Initializable{
    FoosballHouse foosballHouse = FoosballHouse.getInstance();
    Tournament tournamentToEdit;
    UseAgain use = UseAgain.getInstance();

    @FXML
    ChoiceBox exitOptions;

    @FXML
    TextField nameTF;

    @FXML
    DatePicker startDate, endDate;

    @FXML
    Button saveButton;


    public void exitOrLogOut(MouseEvent mouseEvent) {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        use.goBack(actionEvent, "screenAdminTournaments.fxml");
    }

    public void saveTournamentChanges(ActionEvent actionEvent) throws IOException {
        foosballHouse.saveTournamentChanges(nameTF.getText(), startDate.getValue(), endDate.getValue(), tournamentToEdit);
        goBack(actionEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tournamentToEdit = foosballHouse.getChosenTournamentToEdit();
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
        loadData();
    }

    public void loadData(){
        nameTF.setText(tournamentToEdit.getName());
        startDate.setValue(tournamentToEdit.getStartDate().toLocalDate());
        endDate.setValue(tournamentToEdit.getEndDate().toLocalDate());
    }
}
