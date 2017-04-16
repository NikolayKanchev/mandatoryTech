package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.FoosballHouse;
import model.Player;
import model.Tournament;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/14/2017.
 */
public class ContAdminPlayerEdit implements Initializable{
    private FoosballHouse foosballHouse = FoosballHouse.getInstance();
    private Player playerToEdit;
    private UseAgain use = UseAgain.getInstance();

    @FXML
    ChoiceBox exitOptions;

    @FXML
    TextField nameTF, eMailTF, passTF, statusTF;

    @FXML
    DatePicker dateOfBirth;

    @FXML
    Button saveButton;

    @FXML
    Label redLabel;


    public void exitOrLogOut(MouseEvent mouseEvent) {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        use.goBack(actionEvent, "screenAdminPlayers.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerToEdit = foosballHouse.getChosenPlayerToEdit();
        loadData();
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
    }

    public void loadData(){
        nameTF.setText(playerToEdit.getName());
        dateOfBirth.setValue(playerToEdit.getDateOfBirth().toLocalDate());
        eMailTF.setText(playerToEdit.getMail());
        passTF.setText(playerToEdit.getPassword());
        statusTF.setText(playerToEdit.getStatus());
    }

    public void savePlayerChanges(ActionEvent actionEvent) throws IOException {
        foosballHouse.savePlayerChanges(nameTF.getText(), dateOfBirth.getValue(), eMailTF.getText(), passTF.getText(), statusTF.getText(), playerToEdit.getId());
        goBack(actionEvent);
    }
}
